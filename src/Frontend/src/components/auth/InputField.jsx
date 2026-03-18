export default function InputField({ label, value, onChange, error, type="text" }) {
  const id = label.toLowerCase();

  return (
    <div>
      <label htmlFor={id} className="text-gray-300 text-sm">
        {label}
      </label>

      <input
        id={id}
        type={type}
        value={value}
        onChange={onChange}
        className="w-full p-3 rounded bg-gray-800 text-white mt-1"
      />

      {error && <p className="text-red-400 text-xs">{error}</p>}
    </div>
  );
}