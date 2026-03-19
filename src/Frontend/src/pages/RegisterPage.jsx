import { useRegisterForm } from '../hooks/useRegisterForm';
import RegisterForm from '../components/auth/RegisterForm';
import SuccessMessage from '../components/auth/SuccessMessage';

export default function RegisterPage({ goToLogin = () => {} }) {
  const data = useRegisterForm();

  if (data.success) {
    return <SuccessMessage onReset={goToLogin} />;
  }

  return (
    <div className="p-10">
      <RegisterForm {...data} onSubmit={data.submit} />

      <button onClick={goToLogin} className="text-blue-400 mt-3">
        Ir a login
      </button>
    </div>
  );
}