import { Form, Input, Typography } from 'antd';
import type { CSSProperties } from 'react';
import type { Rule } from 'antd/es/form';
import { Color } from '../constants';

const { Text } = Typography;

interface FormInputProps {
  name: string;
  label: string;
  placeholder: string;
  height?: number | string;
  width?: number | string;
  type?: 'text' | 'password';
  rules?: Rule[];
  styles?: CSSProperties;
}

function FormInput(props: FormInputProps) {
  const {
    name,
    label,
    placeholder,
    height = 42,
    width = '100%',
    type = 'text',
    rules,
    styles
  } = props;

  return (
    <Form.Item
      name={name}
      label={<Text style={{ fontSize: 16, fontWeight: 500 }}>{label}</Text>}
      required={false}
      rules={rules}
      style={{ marginBottom: 22 }}
    >
      {type === 'password' ? (
        <Input.Password
          placeholder={placeholder}
          style={{ width, height, fontSize: 16, fontWeight: 500, color: Color.BLACK, ...styles }}
        />
      ) : (
        <Input
          placeholder={placeholder}
          style={{ width, height, fontSize: 16, fontWeight: 500, color: Color.BLACK, ...styles }}
        />
      )}
    </Form.Item>
  );
}

export default FormInput;