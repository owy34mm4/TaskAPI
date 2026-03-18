import { describe, test, expect, vi } from 'vitest';
import { render, screen, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom/vitest';
import RegisterPage from '../pages/RegisterPage';

// 👇 ESTE VA AQUÍ (ANTES DE describe)
vi.mock('../hooks/useRegisterForm', () => ({
  useRegisterForm: () => ({
    success: false,
    submit: () => {},
    loading: false,
    form: {
      nombre: '',
      correo: '',
      usuario: '',
      password: ''
    },
    handleChange: () => () => {},
    errors: {
      general: 'Requerido'
    }
  })
}));

describe('Register Form', () => {

  test('muestra errores si está vacío', () => {
    render(<RegisterPage />);

    fireEvent.click(screen.getAllByRole('button')[0]);

    expect(
      screen.getByText(/requerido/i)
    ).toBeInTheDocument();
  });

});