export default function SuccessMessage({ onReset }) {
  return (
    <div className="text-white text-center">
      <h2>Cuenta creada</h2>
      <button onClick={onReset}>Ir al login</button>
    </div>
  );
}