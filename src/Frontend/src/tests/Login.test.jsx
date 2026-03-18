import { describe, test, expect } from 'vitest';
import { render, screen, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom/vitest';
import LoginPage from '../pages/LoginPage';

describe('Login Form', () => {

  test('error si campos vacíos', () => {
    render(<LoginPage />);

    fireEvent.click(screen.getByText(/login/i));

    expect(
      screen.getByText(/llena todos los campos/i)
    ).toBeInTheDocument();
  });

});