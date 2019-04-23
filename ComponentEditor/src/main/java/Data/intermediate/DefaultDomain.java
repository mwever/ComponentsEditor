package Data.intermediate;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
	    use = JsonTypeInfo.Id.NAME,
	    include = JsonTypeInfo.As.PROPERTY,
	    property = "type")
	@JsonSubTypes({
	    @Type(value = BooleanParameterDomain.class, name = "bool"),
	    @Type(value = NumericParameterDomain.class, name = "number"),
	    @Type(value = CategoricalParameterDomain.class, name = "cat")})
public interface DefaultDomain {

}
