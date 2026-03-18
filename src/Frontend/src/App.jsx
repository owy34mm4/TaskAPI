import { useState } from 'react';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';

export default function App() {
  const [page, setPage] = useState('login');

  return page === 'login'
    ? <LoginPage goToRegister={() => setPage('register')} />
    : <RegisterPage goToLogin={() => setPage('login')} />;
}