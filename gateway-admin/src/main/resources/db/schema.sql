CREATE TABLE IF NOT EXISTS filter_rules (
    id BIGSERIAL PRIMARY KEY,
    rule_name VARCHAR(128) NOT NULL,
    description VARCHAR(512),
    device_id VARCHAR(64),
    sensor_code VARCHAR(64) NOT NULL,
    operator VARCHAR(16) NOT NULL,
    threshold DOUBLE PRECISION NOT NULL,
    action_type VARCHAR(32),
    action_target VARCHAR(256),
    transform_formula VARCHAR(512),
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_filter_rules_device_id ON filter_rules(device_id);
CREATE INDEX IF NOT EXISTS idx_filter_rules_sensor_code ON filter_rules(sensor_code);
CREATE INDEX IF NOT EXISTS idx_filter_rules_enabled ON filter_rules(enabled);

INSERT INTO filter_rules (rule_name, description, device_id, sensor_code, operator, threshold, action_type, action_target, transform_formula, enabled) VALUES
('温度过高告警', '当温度传感器A数值大于80℃时触发告警', 'device-001', 'sensor-A', 'GT', 80.0, 'ALARM', 'http://alert-service/api/alerts', NULL, TRUE),
('华氏温度告警', '华氏度传感器D转摄氏度后大于80℃时告警（示例公式：(value-32)/1.8）', 'device-003', 'sensor-D', 'GT', 80.0, 'ALARM', 'http://alert-service/api/alerts', '(value - 32) / 1.8', TRUE),
('压力过低告警', '当压力传感器B数值小于50kPa时触发告警', 'device-001', 'sensor-B', 'LT', 50.0, 'ALARM', 'http://alert-service/api/alerts', NULL, TRUE),
('湿度联动控制', '当湿度传感器C数值大于等于90%RH时开启除湿机', 'device-002', 'sensor-C', 'GTE', 90.0, 'DEVICE_CONTROL', 'device/dehumidifier-001/on', NULL, TRUE)
ON CONFLICT DO NOTHING;
