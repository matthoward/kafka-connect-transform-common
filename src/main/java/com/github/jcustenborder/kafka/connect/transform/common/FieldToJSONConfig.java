package com.github.jcustenborder.kafka.connect.transform.common;

import com.github.jcustenborder.kafka.connect.utils.config.ConfigKeyBuilder;
import com.github.jcustenborder.kafka.connect.utils.config.ConfigUtils;
import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.data.Schema;

import java.util.Map;

public class FieldToJSONConfig extends AbstractConfig {
    public final String fieldName;
    public final Schema.Type outputSchema;
    public final boolean schemasEnable;

    public static final String FIELD_NAME_CONFIG = "field.name";
    public static final String FIELD_NAME_DOC = "The field name to be transformed into json. " +
            "It should be a field of type STRUCT, MAP or ARRAY";
    public static final String OUTPUT_SCHEMA_CONFIG = "output.schema.type";
    public static final String OUTPUT_SCHEMA_DOC = "The connect schema type to output the converted JSON as.";
    public static final String SCHEMAS_ENABLE_CONFIG = "schemas.enable";
    public static final String SCHEMAS_ENABLE_DOC = "Flag to determine if the JSON data should include the schema.";


    public FieldToJSONConfig(Map<String, ?> settings) {
        super(config(), settings);
        this.fieldName = getString(FIELD_NAME_CONFIG);
        this.outputSchema = ConfigUtils.getEnum(Schema.Type.class, this, OUTPUT_SCHEMA_CONFIG);
        this.schemasEnable = getBoolean(SCHEMAS_ENABLE_CONFIG);
    }

    public static ConfigDef config() {
        return new ConfigDef()
                .define(
                        ConfigKeyBuilder.of(FIELD_NAME_CONFIG, ConfigDef.Type.STRING)
                                .documentation(FIELD_NAME_DOC)
                                .importance(ConfigDef.Importance.HIGH)
                                .build()
                )
                .define(
                        ConfigKeyBuilder.of(OUTPUT_SCHEMA_CONFIG, ConfigDef.Type.STRING)
                                .documentation(OUTPUT_SCHEMA_DOC)
                                .defaultValue(Schema.Type.STRING.toString())
                                .validator(
                                        ConfigDef.ValidString.in(
                                                Schema.Type.STRING.toString(),
                                                Schema.Type.BYTES.toString()
                                        )
                                )
                                .importance(ConfigDef.Importance.MEDIUM)
                                .build()
                ).define(
                        ConfigKeyBuilder.of(SCHEMAS_ENABLE_CONFIG, ConfigDef.Type.BOOLEAN)
                                .documentation(SCHEMAS_ENABLE_DOC)
                                .defaultValue(false)
                                .importance(ConfigDef.Importance.MEDIUM)
                                .build()
                );
    }


}
