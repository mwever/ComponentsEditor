package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import Data.intermediate.BooleanParameterDomain;
import Data.intermediate.CategoricalParameterDomain;
import Data.intermediate.DefaultDomain;
import Data.intermediate.Dependency;
import Data.intermediate.IntermediateComponent;
import Data.intermediate.Kitten;
import Data.intermediate.NumericParameterDomain;
import Data.intermediate.Parameter;
import Data.intermediate.ProvidedInterface;
import Data.intermediate.RequiredInterface;
import Data.intermediate.SelectionType;
import Service.ComponentService;
import hasco.model.Component;
import hasco.model.ParameterDomain;
import jaicore.basic.sets.SetUtil.Pair;

@RestController
@ComponentScan(basePackageClasses = ComponentService.class)
@RequestMapping("/components")
public class ComponentsController {
	@Autowired
	private ComponentService componentService;

	@RequestMapping(method = RequestMethod.GET)
	public Collection<Component> getAllComponents() {
		return this.componentService.getAllComponents();
	}

	@RequestMapping(value = "/{compName}", method = RequestMethod.GET)
	public Component getComponentByName(@PathVariable("compName") final String name) {
		return this.componentService.getComponentByName(name);
	}

	@RequestMapping(value = "/{compName}", method = RequestMethod.DELETE)
	public void deleteComponentbyName(@PathVariable("compName") final String name) {
		this.componentService.removeComponentByName(name);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public void updateComponent(@RequestBody final String str) throws IOException {
		System.out.println("str: " + str);
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		IntermediateComponent icomp = mapper.readValue(str, IntermediateComponent.class);
		Component comp = parseComponent(icomp);
		this.componentService.insertComponent(comp);

	}

	@RequestMapping(method = RequestMethod.POST)
	public void insertComponent(@RequestBody final String str) throws IOException {
		System.out.println("str: " + str);
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		IntermediateComponent icomp = mapper.readValue(str, IntermediateComponent.class);
		Component comp = parseComponent(icomp);
		this.componentService.updateComponent(comp);
	}

	public static Component parseComponent(IntermediateComponent icomp) throws IOException {

		if (icomp.getName() == "") {
			throw new IllegalArgumentException("Components must have a name");
		}
		Component comp = new Component(icomp.getName());

		if (icomp.getProvidedInterfaces() != null) {
			List<ProvidedInterface> provI = icomp.getProvidedInterfaces();
			for (ProvidedInterface pI : provI) {
				if(!icomp.getName().equals(pI.getInterfaces())) {
				comp.addProvidedInterface(pI.getInterfaces());}
				
			}
		}

		if (icomp.getRequiredInterfaces() != null) {
			List<RequiredInterface> reqI = icomp.getRequiredInterfaces();
			for (RequiredInterface rI : reqI) {
				comp.addRequiredInterface(rI.getId(), rI.getName());
			}
		}

		if (icomp.getParameters() != null) {
			List<Parameter> param = icomp.getParameters();
			for (Parameter p : param) {
				if (p.getDefaultDomain() instanceof NumericParameterDomain) {
					Data.intermediate.NumericParameterDomain inp = (Data.intermediate.NumericParameterDomain) p
							.getDefaultDomain();
					hasco.model.NumericParameterDomain np = new hasco.model.NumericParameterDomain(inp.isInteger(),
							inp.getMin(), inp.getMax());
					comp.addParameter(new hasco.model.Parameter(p.getName(), np, inp.getDefaultValue()));
				} else if (p.getDefaultDomain() instanceof CategoricalParameterDomain) {
					Data.intermediate.CategoricalParameterDomain icp = (Data.intermediate.CategoricalParameterDomain) p
							.getDefaultDomain();
					String[] values = new String[icp.getValues().size()];
					int counter = 0;
					for (Kitten k : icp.getValues()) {
						values[counter] = k.getName();
						counter++;
					}
					hasco.model.CategoricalParameterDomain cp = new hasco.model.CategoricalParameterDomain(values);
					comp.addParameter(new hasco.model.Parameter(p.getName(), cp, icp.getDefaultValue()));
				} else if (p.getDefaultDomain() instanceof BooleanParameterDomain) {
					Data.intermediate.BooleanParameterDomain ibp = (Data.intermediate.BooleanParameterDomain) p
							.getDefaultDomain();
					hasco.model.BooleanParameterDomain bp = new hasco.model.BooleanParameterDomain();
					comp.addParameter(new hasco.model.Parameter(p.getName(), bp,"")); //, ibp.getDefaultValue()				}
			}
		}

		if (icomp.getDependencies() != null) {
			List<Dependency> depen = icomp.getDependencies();
			for (Dependency d : depen) {
				hasco.model.Dependency dep = convertToDependency(d.getPre(), d.getPost());
				comp.addDependency(dep);
			}
		}
		}
		comp.getProvidedInterfaces().remove(comp.getName());
		return comp;
		
	}

	private static hasco.model.Dependency convertToDependency(final String pre, final String post) {
		Collection<Collection<Pair<hasco.model.Parameter, ParameterDomain>>> preCollection = new ArrayList<>();
		preCollection.add(initPairofParameterAndDomain(pre));
		ArrayList<Pair<hasco.model.Parameter, ParameterDomain>> postCollection = initPairofParameterAndDomain(post);
		return new hasco.model.Dependency(preCollection, postCollection);
	}

	private static ArrayList<Pair<hasco.model.Parameter, ParameterDomain>> initPairofParameterAndDomain(String input) {

		input = input.trim();
		input = input.replaceAll("\\s+", "");

		String[] andSplitted = input.split("and");
		System.out.println(Arrays.toString(andSplitted));

		ArrayList<Pair<hasco.model.Parameter, ParameterDomain>> output = new ArrayList<>();

		for (String s : andSplitted) {

			String[] inSplitted = s.split("in");
			System.out.println(Arrays.toString(inSplitted));
			String preName = inSplitted[0];
			System.out.println(preName);

			if (inSplitted[1].contains("{")) {
				String catString = inSplitted[1].substring(inSplitted[1].indexOf('{') + 1, inSplitted[1].indexOf('}'));
				String[] catValues = catString.split(",");

				if (catValues.length == 2) {
					if (catValues[0].equalsIgnoreCase("true") && catValues[1].equalsIgnoreCase("false")) {
						hasco.model.BooleanParameterDomain preDomain = new hasco.model.BooleanParameterDomain();
						hasco.model.Parameter preParam = new hasco.model.Parameter(preName, preDomain, null);
						output.add(new Pair<hasco.model.Parameter, ParameterDomain>(preParam, preDomain));

					} else {
						if (catValues[0].equalsIgnoreCase("false") && catValues[1].equalsIgnoreCase("true")) {
							hasco.model.BooleanParameterDomain preDomain = new hasco.model.BooleanParameterDomain();
							hasco.model.Parameter preParam = new hasco.model.Parameter(preName, preDomain, null);
							output.add(new Pair<hasco.model.Parameter, ParameterDomain>(preParam, preDomain));
						} else {
							hasco.model.CategoricalParameterDomain preDomain = new hasco.model.CategoricalParameterDomain(
									catValues);
							hasco.model.Parameter preParam = new hasco.model.Parameter(preName, preDomain, null);
							output.add(new Pair<hasco.model.Parameter, ParameterDomain>(preParam, preDomain));
						}
					}

				} else {
					hasco.model.CategoricalParameterDomain preDomain = new hasco.model.CategoricalParameterDomain(
							catValues);
					hasco.model.Parameter preParam = new hasco.model.Parameter(preName, preDomain, null);
					output.add(new Pair<hasco.model.Parameter, ParameterDomain>(preParam, preDomain));
				}

			} else if (inSplitted[1].contains("[")) {
				String tmp = inSplitted[1].substring(inSplitted[1].indexOf('[') + 1, inSplitted[1].indexOf(']'));
				String[] intervalValues = tmp.split(",");
				double firstValue = Double.parseDouble(intervalValues[0]);
				double secondValue = Double.parseDouble(intervalValues[1]);
				boolean isInteger;
				if (intervalValues[0].contains(".")) {
					isInteger = false;
					hasco.model.NumericParameterDomain preDomain = new hasco.model.NumericParameterDomain(isInteger,
							firstValue, secondValue);
					hasco.model.Parameter preParam = new hasco.model.Parameter(preName, preDomain, null);
					output.add(new Pair<hasco.model.Parameter, ParameterDomain>(preParam, preDomain));
				} else {
					isInteger = true;
					hasco.model.NumericParameterDomain preDomain = new hasco.model.NumericParameterDomain(isInteger,
							firstValue, secondValue);
					hasco.model.Parameter preParam = new hasco.model.Parameter(preName, preDomain, null);
					output.add(new Pair<hasco.model.Parameter, ParameterDomain>(preParam, preDomain));
				}
			}
		}
		System.out.println(output.toString());
		return output;
	}

	public static IntermediateComponent reparseComponent(Component comp) {
		// Name
		IntermediateComponent output = new IntermediateComponent(comp.getName());

		// Provided Interfaces
		ArrayList<ProvidedInterface> provIList = new ArrayList<>();
		for (String provI : comp.getProvidedInterfaces()) {
			ProvidedInterface intProvI = new ProvidedInterface(provI);
			provIList.add(intProvI);
		}
		output.setProvidedInterfaces(provIList);

		// Required Interfaces
		ArrayList<RequiredInterface> reqIList = new ArrayList<>();
		for (Entry<String, String> entry : comp.getRequiredInterfaces().entrySet()) {
			RequiredInterface intReqI = new RequiredInterface(entry.getKey(), entry.getValue());
			reqIList.add(intReqI);
		}
		output.setRequiredInterfaces(reqIList);

		// Parameter
		ArrayList<Parameter> paramList = new ArrayList<>();
		for (hasco.model.Parameter param : comp.getParameters().getLinearization()) {
			//String type = "";
			//String typeName ="";
			DefaultDomain defaultdom = null;
			if (param.getDefaultDomain() instanceof hasco.model.NumericParameterDomain) {
				//type = "number";
				//typeName = "Number";
				defaultdom = new NumericParameterDomain(
						((hasco.model.NumericParameterDomain) param.getDefaultDomain()).getMin(),
						((hasco.model.NumericParameterDomain) param.getDefaultDomain()).getMax(),
						((hasco.model.NumericParameterDomain) param.getDefaultDomain()).isInteger(), 
						(double) param.getDefaultValue());
			} else {
				if (param.getDefaultDomain() instanceof hasco.model.BooleanParameterDomain) {
					//type = "bool";
					//typeName ="Bool";
					defaultdom = new BooleanParameterDomain();//(String) param.getDefaultValue()
					((BooleanParameterDomain) defaultdom).setDefaultValue((String) param.getDefaultValue());
				} else {
					//type = "cat";
					//typeName ="Cat";
					ArrayList<Kitten> kit = new ArrayList<Kitten>();
					for (String str : ((hasco.model.CategoricalParameterDomain) param.getDefaultDomain()).getValues()) {
						kit.add(new Kitten(str));
					}
					defaultdom = new CategoricalParameterDomain(kit, ((String) param.getDefaultValue()));
				}
			}
			/*
			 * SelectionType selction; if(param.getDefaultDomain() instanceof
			 * hasco.model.NumericParameterDomain) { selction = new SelectionType("Number",
			 * new NumericParameterDomain( ((hasco.model.NumericParameterDomain)
			 * param.getDefaultDomain()).getMin(), ((hasco.model.NumericParameterDomain)
			 * param.getDefaultDomain()).getMax(), ((hasco.model.NumericParameterDomain)
			 * param.getDefaultDomain()).isInteger(), type, (double)
			 * param.getDefaultValue())); } else { if(param.getDefaultDomain() instanceof
			 * hasco.model.BooleanParameterDomain) { selction = new SelectionType("Bool",
			 * new BooleanParameterDomain(new String[] { "true", "false" }, type)); }else {
			 * 
			 * ArrayList<Kitten> kit = new ArrayList<Kitten>(); for (String str :
			 * ((hasco.model.CategoricalParameterDomain)
			 * param.getDefaultDomain()).getValues()) { kit.add(new Kitten(str)); } selction
			 * = new SelectionType("Cat", new CategoricalParameterDomain(kit, type,
			 * ((String) param.getDefaultValue()))); } }
			 */
			
			
			paramList.add(new Parameter(param.getName(), defaultdom));
			/*
			 * paramList.get(paramList.size()-1).setTypes(new SelectionType[] { new
			 * SelectionType("Cat", new CategoricalParameterDomain(new ArrayList<Kitten>(),
			 * "")), new SelectionType("Number", new NumericParameterDomain(0, 0, false,
			 * 0)), new SelectionType("Bool", new BooleanParameterDomain()) });
			 * paramList.get(paramList.size()-1).setParamTypeName(typeName);
			 */
			
		}
		output.setParameters(paramList);

		// Dependency
		ArrayList<Dependency> depList = new ArrayList<>();
		for (hasco.model.Dependency dep : comp.getDependencies()) {
			Pair<String, String> prePost = parseDependencyToStringPair(dep);
			Dependency intDep = new Dependency(prePost.getX(), prePost.getY());
			depList.add(intDep);
		}
		output.setDependencies(depList);

		return output;

	}

	private static Pair<String, String> parseDependencyToStringPair(hasco.model.Dependency dep) {

		// Premise parse
		String pre = "";

		int counter = 0;

		for (Collection<Pair<hasco.model.Parameter, ParameterDomain>> pairs : dep.getPremise()) {
			int end = pairs.size();
			for (Pair<hasco.model.Parameter, ParameterDomain> d : pairs) {
				if (d.getY() instanceof hasco.model.NumericParameterDomain) {
					if (counter < end - 1) {
						pre += d.getX().getName() + " in " + "["
								+ ((hasco.model.NumericParameterDomain) d.getY()).getMin() + ","
								+ ((hasco.model.NumericParameterDomain) d.getY()).getMax() + "]";
						pre += " and ";
					} else {
						pre += d.getX().getName() + " in " + "["
								+ ((hasco.model.NumericParameterDomain) d.getY()).getMin() + ","
								+ ((hasco.model.NumericParameterDomain) d.getY()).getMax() + "]";
					}
				} else {
					if (d.getY() instanceof hasco.model.CategoricalParameterDomain) {
						if (d.getY() instanceof hasco.model.BooleanParameterDomain) {
							if (counter < end - 1) {
								pre += d.getX().getName() + " in " + "{true,false}";
								pre += " and ";
							} else {
								pre += d.getX().getName() + " in " + "{true,false}";
							}
						} else {
							if (counter < end - 1) {
								pre += d.getX().getName() + " in " + "{";
								int counterValue = 0;
								int endValue = ((hasco.model.CategoricalParameterDomain) d.getY()).getValues().length;
								for (String str : ((hasco.model.CategoricalParameterDomain) d.getY()).getValues()) {
									if (counterValue < endValue - 1) {
										pre += str + ",";
									} else {
										pre += str;
										pre += "}";
										pre += " and ";
									}
								}

							} else {
								pre += d.getX().getName() + " in " + "{";
								int counterValue = 0;
								int endValue = ((hasco.model.CategoricalParameterDomain) d.getY()).getValues().length;
								for (String str : ((hasco.model.CategoricalParameterDomain) d.getY()).getValues()) {
									if (counterValue < endValue - 1) {
										pre += str + ",";
									} else {
										pre += str;
										pre += "}";
									}
								}
							}
						}
					}
				}

				counter++;
			}
		}

		// Conclusion parse

		String post = "";
		counter = 0;
		int end = dep.getConclusion().size();

		for (Pair<hasco.model.Parameter, ParameterDomain> pairs : dep.getConclusion()) {
			if (pairs.getY() instanceof hasco.model.NumericParameterDomain) {
				if (counter < end - 1) {
					post += pairs.getX().getName() + " in " + "["
							+ ((hasco.model.NumericParameterDomain) pairs.getY()).getMin() + ","
							+ ((hasco.model.NumericParameterDomain) pairs.getY()).getMax() + "]";
					post += " and ";
				} else {
					post += pairs.getX().getName() + " in " + "["
							+ ((hasco.model.NumericParameterDomain) pairs.getY()).getMin() + ","
							+ ((hasco.model.NumericParameterDomain) pairs.getY()).getMax() + "]";
				}
			} else {
				if (pairs.getY() instanceof hasco.model.CategoricalParameterDomain) {
					if (pairs.getY() instanceof hasco.model.BooleanParameterDomain) {
						if (counter < end - 1) {
							post += pairs.getX().getName() + " in " + "{true,false}";
							post += " and ";
						} else {
							post += pairs.getX().getName() + " in " + "{true,false}";
						}
					} else {
						if (counter < end - 1) {
							post += pairs.getX().getName() + " in " + "{";
							int counterValue = 0;
							int endValue = ((hasco.model.CategoricalParameterDomain) pairs.getY()).getValues().length;
							for (String str : ((hasco.model.CategoricalParameterDomain) pairs.getY()).getValues()) {
								if (counterValue < endValue) {
									post += str + ",";
								} else {
									post += str;
									post += "}";
								}
							}
							post+= " and ";

						} else {
							post += pairs.getX().getName() + " in " + "{";
							int counterValue = 0;
							int endValue = ((hasco.model.CategoricalParameterDomain) pairs.getY()).getValues().length;
							for (String str : ((hasco.model.CategoricalParameterDomain) pairs.getY()).getValues()) {
								if (counterValue < endValue) {
									post += str + ",";
								} else {
									post += str;
									post += "}";
								}
							}
						}
					}
				}

				counter++;
			}
		}

		return new Pair<>(pre, post);

	}
}
