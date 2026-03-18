export default function PasswordStrength({ password }) {
  const score =
    (password.length >= 8) +
    /[A-Z]/.test(password) +
    /[0-9]/.test(password);

  return <p className="text-xs text-gray-400">Nivel: {score}/3</p>;
}