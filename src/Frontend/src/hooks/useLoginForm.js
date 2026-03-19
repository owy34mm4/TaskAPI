import { useState } from 'react';

export function useLoginForm() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const submit = async () => {
    if (!email || !password) {
      setError('Por favor llena todos los campos');
      return;
    }

    setLoading(true);
    setError('');

    await new Promise(r => setTimeout(r, 1500));

    setLoading(false);
    setError('Credenciales inválidas');
  };

  return {
    email,
    password,
    showPassword,
    loading,
    error,
    setEmail,
    setPassword,
    setShowPassword,
    submit
  };
}