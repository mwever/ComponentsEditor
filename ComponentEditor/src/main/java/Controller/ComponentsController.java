package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import Data.intermediate.Dependency;
import Data.intermediate.IntermediateComponent;
import Data.intermediate.Kitten;
import Data.intermediate.NumericParameterDomain;
import Data.intermediate.Parameter;
import Data.intermediate.ProvidedInterface;
import Data.intermediate.RequiredInterface;
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
				comp.addProvidedInterface(pI.getInterfaces());
			}
		}

		if (icomp.getRequiredInterfaces() != null) {
			List<RequiredInterface> reqI = icomp.getRequiredInterfaces();
			for (RequiredInterface rI : reqI) {
				comp.addRequiredInterface(Long.toString(rI.getId()), rI.getName());
			}
		}

		if (icomp.getParameters() != null) {
			List<Parameter> param = icomp.getParameters();
			for (Parameter p : param) {
				if (p.getDefaultDomain() instanceof NumericParameterDomain) {
					Data.intermediate.NumericParameterDomain inp = (Data.intermediate.NumericParameterDomain) p.getDefaultDomain();
					hasco.model.NumericParameterDomain np = new hasco.model.NumericParameterDomain(inp.isInteger(), inp.getMin(), inp.getMax());
					comp.addParameter(new hasco.model.Parameter(p.getName(), np, inp.getDefaultValue()));
				} else if (p.getDefaultDomain() instanceof CategoricalParameterDomain) {
					Data.intermediate.CategoricalParameterDomain icp = (Data.intermediate.CategoricalParameterDomain) p.getDefaultDomain();
					String[] values = new String[icp.getValues().size()];
					int counter = 0;
					for (Kitten k : icp.getValues()) {
						values[counter] = k.getName();
						counter++;
					}
					hasco.model.CategoricalParameterDomain cp = new hasco.model.CategoricalParameterDomain(values);
					comp.addParameter(new hasco.model.Parameter(p.getName(), cp, icp.getDefaultValue()));
				} else if (p.getDefaultDomain() instanceof BooleanParameterDomain) {
					Data.intermediate.BooleanParameterDomain ibp = (Data.intermediate.BooleanParameterDomain) p.getDefaultDomain();
					hasco.model.BooleanParameterDomain bp = new hasco.model.BooleanParameterDomain();
					comp.addParameter(new hasco.model.Parameter(p.getName(), bp, ibp.getDefaultValue()));
				}
			}
		}

		if (icomp.getDependencies() != null) {
			List<Dependency> depen = icomp.getDependencies();
			for (Dependency d : depen) {
				hasco.model.Dependency dep = convertToDependency(d.getPre(), d.getPost());
				comp.addDependency(dep);
			}
		}

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

		ArrayList<Pair<hasco.model.Parameter, ParameterDomain>> output = new ArrayList<>();

		for (String s : andSplitted) {

			String[] inSplitted = s.split("in");
			String preName = inSplitted[0];

			if (inSplitted[1].contains("{")) {
				String catString = inSplitted[1].substring(inSplitted[1].indexOf('{') + 1, inSplitted[1].indexOf('}') - 1);
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
						}
					}
					hasco.model.CategoricalParameterDomain preDomain = new hasco.model.CategoricalParameterDomain(catValues);
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
					hasco.model.NumericParameterDomain preDomain = new hasco.model.NumericParameterDomain(isInteger, firstValue, secondValue);
					hasco.model.Parameter preParam = new hasco.model.Parameter(preName, preDomain, null);
					output.add(new Pair<hasco.model.Parameter, ParameterDomain>(preParam, preDomain));
				} else {
					isInteger = true;
					hasco.model.NumericParameterDomain preDomain = new hasco.model.NumericParameterDomain(isInteger, firstValue, secondValue);
					hasco.model.Parameter preParam = new hasco.model.Parameter(preName, preDomain, null);
					output.add(new Pair<hasco.model.Parameter, ParameterDomain>(preParam, preDomain));
				}
			}
		}
		return output;
	}
}
