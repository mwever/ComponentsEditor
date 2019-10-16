package Utils;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.NumericNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import hasco.model.BooleanParameterDomain;
import hasco.model.CategoricalParameterDomain;
import hasco.model.Component;
import hasco.model.Dependency;
import hasco.model.NumericParameterDomain;
import hasco.model.Parameter;
import hasco.model.ParameterDomain;
import jaicore.basic.sets.SetUtil.Pair;

@Service
public class ComponentsSerializer {

	public static String componentCollectionToJSONRepository(final Collection<Component> componentsToSerialize,
			final String repositoryName) throws JsonProcessingException {

		JsonNodeFactory factory = new JsonNodeFactory(true);

		ObjectNode root = factory.objectNode();
		root.set("repository", factory.textNode(repositoryName));

		ArrayNode componentsArray = factory.arrayNode();

		for (Component c : componentsToSerialize) {
			ObjectNode comp = factory.objectNode();
			comp.set("name", factory.textNode(c.getName()));

			ArrayNode provI = factory.arrayNode();
			for (String provInter : c.getProvidedInterfaces()) {
				provI.add(provInter);
			}
			comp.set("providedInterface", provI);

			ArrayNode reqI = factory.arrayNode();
			for (String reqInterkey : c.getRequiredInterfaces().keySet()) {
				ObjectNode reqInter = factory.objectNode();
				reqInter.set("id", factory.textNode(reqInterkey));
				reqInter.set("name", factory.textNode(c.getRequiredInterfaces().get(reqInterkey)));
				reqI.add(reqInter);
			}
			comp.set("requiredInterface", reqI);

			ArrayNode parameter = factory.arrayNode();
			for (Parameter param : c.getParameters()) {
				ObjectNode para = factory.objectNode();
				para.set("name", factory.textNode(param.getName()));
				String typeName = "";
				if (param.isCategorical()) {
					if (param.getDefaultDomain() instanceof BooleanParameterDomain) {
						typeName = "boolean";
					} else {
						typeName = "cat";
					}
				}
				if (param.isNumeric()) {
					if (((NumericParameterDomain) param.getDefaultDomain()).isInteger()) {
						typeName = "int";
					} else {
						typeName = "double";
					}

				}
				para.set("type", factory.textNode(typeName));
				para.set("default", factory.textNode(param.getDefaultValue().toString()));

				if (param.isCategorical() && !(param.getDefaultDomain() instanceof BooleanParameterDomain)) {
					ArrayNode values = factory.arrayNode();
					for (String value : ((CategoricalParameterDomain) param.getDefaultDomain()).getValues()) {
						values.add(value);
					}
					para.set("values", values);
				}
				if (param.isNumeric()) {
					NumericNode min = factory.numberNode(((NumericParameterDomain) param.getDefaultDomain()).getMin());
					para.set("min", min);
					NumericNode max = factory.numberNode(((NumericParameterDomain) param.getDefaultDomain()).getMax());
					para.set("max", max);
					
					//dummy values
					NumericNode refineSplits = factory.numberNode(8);
					para.set("refineSplits", refineSplits);
					
					double minIntervalValue = ((NumericParameterDomain) param.getDefaultDomain()).getMax() - ((NumericParameterDomain) param.getDefaultDomain()).getMin() / 1024;
					NumericNode minInterval = factory.numberNode(minIntervalValue);
					para.set("minInterval", minInterval);
					
					
				}

				parameter.add(para);
			}
			comp.set("parameter", parameter);

			ArrayNode dependency = factory.arrayNode();
			for (Dependency dep : c.getDependencies()) {
				ObjectNode depen = factory.objectNode();
				String preName = "";
				String preValues = "";
				String postName = "";
				String postValues = "";
				
				
				for (Collection<Pair<Parameter, ParameterDomain>> coll : dep.getPremise()) {
					for (Pair<Parameter, ParameterDomain> pair : coll) {
						preName = pair.getX().getName();
						if (pair.getX().isCategorical()) {
							if (pair.getY() instanceof BooleanParameterDomain) {
								preValues = "{true,false}";
							} else {
								for (int i = 0; i < ((CategoricalParameterDomain) pair.getY())
										.getValues().length; i++) {
									if (((CategoricalParameterDomain) pair.getY()).getValues().length == 1) {
										preValues = "{" + ((CategoricalParameterDomain) pair.getY()).getValues()[0]
												+ "}";
									} else if (i == 0) {
										preValues = "{" + ((CategoricalParameterDomain) pair.getY()).getValues()[i]
												+ ",";
									} else if (i == ((CategoricalParameterDomain) pair.getY()).getValues().length - 1) {
										preValues = ((CategoricalParameterDomain) pair.getY()).getValues()[i] + "}";
									} else {
										preValues = ((CategoricalParameterDomain) pair.getY()).getValues()[i] + ",";
									}
								}
							}
						}
						if (pair.getX().isNumeric()) {
							preValues = "[" + ((NumericParameterDomain) pair.getY()).getMin() + ","
									+ ((NumericParameterDomain) pair.getY()).getMax() + "]";
						}
					}
				}

				depen.set("pre", new TextNode(preName + " in " + preValues));
				
				for (Pair<Parameter, ParameterDomain> pair : dep.getConclusion()) {
					postName = pair.getX().getName();
					if (pair.getX().isCategorical()) {
						if (pair.getY() instanceof BooleanParameterDomain) {
							postValues = "{true,false}";
						} else {
							for (int i = 0; i < ((CategoricalParameterDomain) pair.getY()).getValues().length; i++) {
								if (((CategoricalParameterDomain) pair.getY()).getValues().length == 1) {
									postValues = "{" + ((CategoricalParameterDomain) pair.getY()).getValues()[0] + "}";
								} else if (i == 0) {
									postValues = "{" + ((CategoricalParameterDomain) pair.getY()).getValues()[i] + ",";
								} else if (i == ((CategoricalParameterDomain) pair.getY()).getValues().length - 1) {
									postValues = ((CategoricalParameterDomain) pair.getY()).getValues()[i] + "}";
								} else {
									postValues = ((CategoricalParameterDomain) pair.getY()).getValues()[i] + ",";
								}
							}
						}
					}
					if (pair.getX().isNumeric()) {
						postValues = "[" + ((NumericParameterDomain) pair.getY()).getMin() + ","
								+ ((NumericParameterDomain) pair.getY()).getMax() + "]";
					}
				}

				depen.set("post", new TextNode(postName + " in " + postValues));
				dependency.add(depen);
			}
			comp.set("dependencies", dependency);

			componentsArray.add(comp);
		}
		root.set("components", componentsArray);

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(root);
	}

	public Collection<Component> JSONRepositoryTocomponentCollection(final String json) {
		return null;
	}

	/*
	 * 
	 * public static void main(final String[] args) throws IOException {
	 * ComponentLoader cl = new ComponentLoader(new
	 * File("C:/Users/Helen/Desktop/mlPlan.json")); String repoString =
	 * componentCollectionToJSONRepository(cl.getComponents(), "My Repository");
	 * System.out.println(repoString); }
	 */

}
