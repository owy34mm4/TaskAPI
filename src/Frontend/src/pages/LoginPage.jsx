import { useLoginForm } from '../hooks/useLoginForm';
import LoginForm from '../components/auth/LoginForm';

export default function LoginPage({ goToRegister }) {
  const data = useLoginForm();

  return (
    <div className="p-10">
      <LoginForm {...data} onSubmit={data.submit} />

      <button onClick={goToRegister} className="text-blue-400 mt-3">
        Ir a registro
      </button>
    </div>
  );
}