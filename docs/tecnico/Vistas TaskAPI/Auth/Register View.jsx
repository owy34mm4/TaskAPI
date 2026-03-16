const { useState } = React;

function App() {
  const [form, setForm] = useState({ nombre: '', correo: '', usuario: '', password: '', confirmPassword: '' });
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirm, setShowConfirm] = useState(false);
  const [loading, setLoading] = useState(false);
  const [errors, setErrors] = useState({});
  const [success, setSuccess] = useState(false);

  const validate = () => {
    const e = {};
    if (!form.nombre.trim()) e.nombre = 'El nombre es requerido.';
    if (!form.correo.trim()) e.correo = 'El correo es requerido.';
    else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(form.correo)) e.correo = 'Correo inválido.';
    if (!form.usuario.trim()) e.usuario = 'El usuario es requerido.';
    if (!form.password) e.password = 'La contraseña es requerida.';
    else if (form.password.length < 8) e.password = 'Mínimo 8 caracteres.';
    if (form.password !== form.confirmPassword) e.confirmPassword = 'Las contraseñas no coinciden.';
    return e;
  };

  const getPasswordStrength = (pwd) => {
    if (!pwd) return { level: 0, label: '', color: '' };
    let score = 0;
    if (pwd.length >= 8) score++;
    if (/[A-Z]/.test(pwd)) score++;
    if (/[0-9]/.test(pwd)) score++;
    if (/[^A-Za-z0-9]/.test(pwd)) score++;
    if (score <= 1) return { level: 1, label: 'Débil', color: 'bg-red-500' };
    if (score === 2) return { level: 2, label: 'Regular', color: 'bg-yellow-500' };
    if (score === 3) return { level: 3, label: 'Buena', color: 'bg-blue-500' };
    return { level: 4, label: 'Fuerte', color: 'bg-green-500' };
  };

  const strength = getPasswordStrength(form.password);

  const handleChange = (field) => (e) => {
    setForm({ ...form, [field]: e.target.value });
    if (errors[field]) setErrors({ ...errors, [field]: null });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const errs = validate();
    if (Object.keys(errs).length > 0) { setErrors(errs); return; }
    setLoading(true);
    setTimeout(() => { setLoading(false); setSuccess(true); }, 1500);
  };

  const EyeIcon = ({ open }) => open ? (
    <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.8} d="M13.875 18.825A10.05 10.05 0 0112 19c-4.478 0-8.268-2.943-9.543-7a9.97 9.97 0 011.563-3.029m5.858.908a3 3 0 114.243 4.243M9.878 9.878l4.242 4.242M9.88 9.88l-3.29-3.29m7.532 7.532l3.29 3.29M3 3l3.59 3.59m0 0A9.953 9.953 0 0112 5c4.478 0 8.268 2.943 9.543 7a10.025 10.025 0 01-4.132 5.411m0 0L21 21" />
    </svg>
  ) : (
    <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.8} d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.8} d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
    </svg>
  );

  if (success) return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-gray-900 via-slate-800 to-gray-900">
      <div className="absolute top-0 left-0 w-full h-full overflow-hidden pointer-events-none">
        <div className="absolute -top-32 -left-32 w-96 h-96 bg-indigo-600 opacity-20 rounded-full filter blur-3xl"></div>
        <div className="absolute -bottom-32 -right-32 w-96 h-96 bg-purple-600 opacity-20 rounded-full filter blur-3xl"></div>
      </div>
      <div className="relative bg-white bg-opacity-5 border border-white border-opacity-10 rounded-2xl shadow-2xl p-10 flex flex-col items-center max-w-sm mx-4 text-center">
        <div className="w-16 h-16 bg-green-500 rounded-full flex items-center justify-center mb-4 shadow-lg">
          <svg className="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2.5} d="M5 13l4 4L19 7" />
          </svg>
        </div>
        <h2 className="text-xl font-bold text-white mb-2">¡Cuenta creada!</h2>
        <p className="text-gray-400 text-sm mb-6">Tu cuenta ha sido registrada exitosamente. Ya puedes iniciar sesión.</p>
        <button onClick={() => { setSuccess(false); setForm({ nombre:'', correo:'', usuario:'', password:'', confirmPassword:'' }); }}
          className="bg-indigo-600 hover:bg-indigo-500 text-white font-semibold px-6 py-2.5 rounded-xl transition">
          Ir al Login
        </button>
      </div>
    </div>
  );

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-gray-900 via-slate-800 to-gray-900 py-10">
      {/* Decorative blobs */}
      <div className="absolute top-0 left-0 w-full h-full overflow-hidden pointer-events-none">
        <div className="absolute -top-32 -left-32 w-96 h-96 bg-indigo-600 opacity-20 rounded-full filter blur-3xl"></div>
        <div className="absolute -bottom-32 -right-32 w-96 h-96 bg-purple-600 opacity-20 rounded-full filter blur-3xl"></div>
      </div>

      <div className="relative w-full max-w-md mx-4">
        {/* Card */}
        <div className="bg-white bg-opacity-5 backdrop-blur-md border border-white border-opacity-10 rounded-2xl shadow-2xl p-8">

          {/* Header */}
          <div className="flex flex-col items-center mb-7">
            <div className="w-14 h-14 bg-indigo-600 rounded-xl flex items-center justify-center shadow-lg mb-4">
              <svg className="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M18 9v3m0 0v3m0-3h3m-3 0h-3m-2-5a4 4 0 11-8 0 4 4 0 018 0zM3 20a6 6 0 0112 0v1H3v-1z" />
              </svg>
            </div>
            <h1 className="text-2xl font-bold text-white tracking-tight">Crear cuenta</h1>
            <p className="text-sm text-gray-400 mt-1">Completa los datos para registrarte</p>
          </div>

          <form onSubmit={handleSubmit} className="space-y-4">

            {/* Nombre */}
            <div>
              <label className="block text-sm font-medium text-gray-300 mb-1.5">Nombre completo</label>
              <div className="relative">
                <span className="absolute inset-y-0 left-3 flex items-center text-gray-400">
                  <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.8} d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
                  </svg>
                </span>
                <input
                  type="text"
                  value={form.nombre}
                  onChange={handleChange('nombre')}
                  placeholder="Juan Pérez"
                  className={`w-full bg-white bg-opacity-10 border ${errors.nombre ? 'border-red-500' : 'border-white border-opacity-10'} text-white placeholder-gray-500 rounded-xl pl-10 pr-4 py-3 text-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent transition`}
                />
              </div>
              {errors.nombre && <p className="text-red-400 text-xs mt-1">{errors.nombre}</p>}
            </div>

            {/* Correo */}
            <div>
              <label className="block text-sm font-medium text-gray-300 mb-1.5">Correo electrónico</label>
              <div className="relative">
                <span className="absolute inset-y-0 left-3 flex items-center text-gray-400">
                  <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.8} d="M16 12a4 4 0 10-8 0 4 4 0 008 0zm0 0v1.5a2.5 2.5 0 005 0V12a9 9 0 10-9 9m4.5-1.206a8.959 8.959 0 01-4.5 1.207" />
                  </svg>
                </span>
                <input
                  type="email"
                  value={form.correo}
                  onChange={handleChange('correo')}
                  placeholder="correo@ejemplo.com"
                  className={`w-full bg-white bg-opacity-10 border ${errors.correo ? 'border-red-500' : 'border-white border-opacity-10'} text-white placeholder-gray-500 rounded-xl pl-10 pr-4 py-3 text-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent transition`}
                />
              </div>
              {errors.correo && <p className="text-red-400 text-xs mt-1">{errors.correo}</p>}
            </div>

            {/* Usuario */}
            <div>
              <label className="block text-sm font-medium text-gray-300 mb-1.5">Nombre de usuario</label>
              <div className="relative">
                <span className="absolute inset-y-0 left-3 flex items-center text-gray-400 text-sm font-bold">@</span>
                <input
                  type="text"
                  value={form.usuario}
                  onChange={handleChange('usuario')}
                  placeholder="juanperez123"
                  className={`w-full bg-white bg-opacity-10 border ${errors.usuario ? 'border-red-500' : 'border-white border-opacity-10'} text-white placeholder-gray-500 rounded-xl pl-8 pr-4 py-3 text-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent transition`}
                />
              </div>
              {errors.usuario && <p className="text-red-400 text-xs mt-1">{errors.usuario}</p>}
            </div>

            {/* Contraseña */}
            <div>
              <label className="block text-sm font-medium text-gray-300 mb-1.5">
                Contraseña
                <span className="ml-2 text-xs font-normal text-indigo-400 bg-indigo-900 bg-opacity-40 px-2 py-0.5 rounded-full">RSA-256</span>
              </label>
              <div className="relative">
                <span className="absolute inset-y-0 left-3 flex items-center text-gray-400">
                  <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.8} d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z" />
                  </svg>
                </span>
                <input
                  type={showPassword ? 'text' : 'password'}
                  value={form.password}
                  onChange={handleChange('password')}
                  placeholder="••••••••"
                  className={`w-full bg-white bg-opacity-10 border ${errors.password ? 'border-red-500' : 'border-white border-opacity-10'} text-white placeholder-gray-500 rounded-xl pl-10 pr-10 py-3 text-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent transition`}
                />
                <button type="button" onClick={() => setShowPassword(!showPassword)}
                  className="absolute inset-y-0 right-3 flex items-center text-gray-400 hover:text-gray-200 transition">
                  <EyeIcon open={showPassword} />
                </button>
              </div>
              {/* Strength bar */}
              {form.password && (
                <div className="mt-2">
                  <div className="flex gap-1 mb-1">
                    {[1,2,3,4].map(i => (
                      <div key={i} className={`h-1 flex-1 rounded-full transition-all duration-300 ${i <= strength.level ? strength.color : 'bg-white bg-opacity-10'}`}></div>
                    ))}
                  </div>
                  <p className={`text-xs ${strength.level <= 1 ? 'text-red-400' : strength.level === 2 ? 'text-yellow-400' : strength.level === 3 ? 'text-blue-400' : 'text-green-400'}`}>
                    Seguridad: {strength.label}
                  </p>
                </div>
              )}
              {errors.password && <p className="text-red-400 text-xs mt-1">{errors.password}</p>}
            </div>

            {/* Confirmar Contraseña */}
            <div>
              <label className="block text-sm font-medium text-gray-300 mb-1.5">Confirmar contraseña</label>
              <div className="relative">
                <span className="absolute inset-y-0 left-3 flex items-center text-gray-400">
                  <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.8} d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z" />
                  </svg>
                </span>
                <input
                  type={showConfirm ? 'text' : 'password'}
                  value={form.confirmPassword}
                  onChange={handleChange('confirmPassword')}
                  placeholder="••••••••"
                  className={`w-full bg-white bg-opacity-10 border ${errors.confirmPassword ? 'border-red-500' : form.confirmPassword && form.confirmPassword === form.password ? 'border-green-500' : 'border-white border-opacity-10'} text-white placeholder-gray-500 rounded-xl pl-10 pr-10 py-3 text-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent transition`}
                />
                <button type="button" onClick={() => setShowConfirm(!showConfirm)}
                  className="absolute inset-y-0 right-3 flex items-center text-gray-400 hover:text-gray-200 transition">
                  <EyeIcon open={showConfirm} />
                </button>
              </div>
              {errors.confirmPassword && <p className="text-red-400 text-xs mt-1">{errors.confirmPassword}</p>}
              {form.confirmPassword && form.confirmPassword === form.password && (
                <p className="text-green-400 text-xs mt-1 flex items-center gap-1">
                  <svg className="w-3.5 h-3.5" fill="currentColor" viewBox="0 0 20 20"><path fillRule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clipRule="evenodd"/></svg>
                  Las contraseñas coinciden
                </p>
              )}
            </div>

            {/* Terms */}
            <div className="flex items-start gap-2 pt-1">
              <input id="terms" type="checkbox" className="mt-0.5 w-4 h-4 rounded border-gray-600 bg-gray-700 text-indigo-500 focus:ring-indigo-500" />
              <label htmlFor="terms" className="text-xs text-gray-400 leading-relaxed">
                Acepto los <a href="#" className="text-indigo-400 hover:text-indigo-300">Términos de Servicio</a> y la <a href="#" className="text-indigo-400 hover:text-indigo-300">Política de Privacidad</a>
              </label>
            </div>

            {/* Submit */}
            <button
              type="submit"
              disabled={loading}
              className="w-full bg-indigo-600 hover:bg-indigo-500 disabled:opacity-60 disabled:cursor-not-allowed text-white font-semibold py-3 rounded-xl transition duration-200 flex items-center justify-center gap-2 shadow-lg mt-2"
            >
              {loading ? (
                <>
                  <svg className="animate-spin w-5 h-5" fill="none" viewBox="0 0 24 24">
                    <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4" />
                    <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8v8H4z" />
                  </svg>
                  Creando cuenta...
                </>
              ) : 'Crear cuenta'}
            </button>
          </form>

          {/* Login link */}
          <p className="text-center text-sm text-gray-500 mt-6">
            ¿Ya tienes cuenta?{' '}
            <a href="#" className="text-indigo-400 hover:text-indigo-300 font-medium transition">Inicia sesión</a>
          </p>
        </div>

        <p className="text-center text-xs text-gray-600 mt-4">© 2026 YourBrand. All rights reserved.</p>
      </div>
    </div>
  );
}