export default function PasswordField({ value, onChange, show, toggle }) {
  return (
    <div>
      <label className="text-gray-300 text-sm">Contraseña</label>

      <div className="flex">
        <input
          type={show ? 'text' : 'password'}
          value={value}
          onChange={onChange}
          className="w-full p-3 bg-gray-800 text-white"
        />

        <button type="button" onClick={toggle}>
          👁
        </button>
      </div>
    </div>
  );
}