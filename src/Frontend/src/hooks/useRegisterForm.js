import { useState } from 'react';

export function useRegisterForm() {
  const [form, setForm] = useState({
    nombre: '',
    correo: '',
    usuario: '',
    password: '',
    confirmPassword: ''
  });

  const [errors, setErrors] = useState({});
  const [loading, setLoading] = useState(false);
  const [success, setSuccess] = useState(false);

  const validate = () => {
    const e = {};

    if (!form.nombre) e.nombre = 'Requerido';
    if (!form.correo) e.correo = 'Requerido';
    if (!form.usuario) e.usuario = 'Requerido';
    if (form.password.length < 8) e.password = 'Min 8';
    if (form.password !== form.confirmPassword)
      e.confirmPassword = 'No coinciden';

    return e;
  };

  const handleChange = (field) => (e) => {
    setForm({ ...form, [field]: e.target.value });
  };

  const submit = async () => {
    const errs = validate();
    if (Object.keys(errs).length) {
      setErrors(errs);
      return;
    }

    setLoading(true);
    await new Promise(r => setTimeout(r, 1500));
    setLoading(false);
    setSuccess(true);
  };

  return {
    form,
    errors,
    loading,
    success,
    setSuccess,
    handleChange,
    submit
  };
}