package abcreate.fb.common.mapper;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import abcreate.fb.common.utils.StringUtils;

/**
 * 手机号(敏感信息)脱敏注解
 * 
 */
public class MobileSerializer extends JsonSerializer<String> {

	@Override
	public void serialize(String value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		gen.writeString(desensitizeMobile(value));
	}

	private String desensitizeMobile(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			return "";
		}
		String leftPad = StringUtils.leftPad(StringUtils.right(mobile, 4), StringUtils.length(mobile), "*");
		return StringUtils.left(mobile, 3).concat(StringUtils.removeStart(leftPad, "***"));
	}
}