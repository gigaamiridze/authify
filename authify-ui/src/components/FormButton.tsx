import type { CSSProperties } from 'react';
import type { ButtonType, ButtonHTMLType, ButtonVariantType } from 'antd/es/button';
import { Button, Form } from 'antd';

interface FormButtonProps {
  title: string;
  width?: number | string;
  height?: number | string;
  type?: ButtonType;
  htmlType?: ButtonHTMLType;
  variant?: ButtonVariantType;
  loading?: boolean;
  disabled?: boolean;
  onClick?: () => void;
  styles?: CSSProperties;
}

function FormButton(props: FormButtonProps) {
  const {
    title,
    width = '100%',
    height = 42,
    type = 'primary',
    htmlType = 'button',
    variant = 'outlined',
    loading = false,
    disabled = false,
    styles
  } = props;

  return (
    <Form.Item style={{ marginTop: 15, marginBottom: 20 }}>
      <Button
        type={type}
        htmlType={htmlType}
        variant={variant}
        loading={loading}
        disabled={disabled}
        style={{ width, height, fontSize: 16, fontWeight: 500, ...styles }}
      >
        {title}
      </Button>
    </Form.Item>
  );
}

export default FormButton;