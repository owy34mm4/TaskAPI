const { useState } = React;

const mockEquipos = [
  { id: 1, nombre: 'Frontend Squad', codigo: 'FE-001', activo: true, maps: 4, pendientes: 7 },
  { id: 2, nombre: 'Backend Core', codigo: 'BE-002', activo: true, maps: 6, pendientes: 3 },
  { id: 3, nombre: 'DevOps Team', codigo: 'DO-003', activo: true, maps: 2, pendientes: 5 },
  { id: 4, nombre: 'QA Automation', codigo: 'QA-004', activo: false, maps: 3, pendientes: 9 },
  { id: 5, nombre: 'Data & Analytics', codigo: 'DA-005', activo: true, maps: 5, pendientes: 2 },
  { id: 6, nombre: 'Mobile Team', codigo: 'MB-006', activo: true, maps: 1, pendientes: 4 },
];

const estadoColors = {
  Pendiente: 'bg-yellow-500 text-yellow-900',
  Completada: 'bg-green-500 text-green-900',
  Eliminada: 'bg-red-500 text-red-900',
};

function App() {
  const [sidebarOpen, setSidebarOpen] = useState(true);
  const [darkMode, setDarkMode] = useState(true);
  const [search, setSearch] = useState('');
  const [activeNav, setActiveNav] = useState('equipos');
  const [page, setPage] = useState(1);
  const [showJoinModal, setShowJoinModal] = useState(false);
  const [showCreateModal, setShowCreateModal] = useState(false);
  const [joinCode, setJoinCode] = useState('');
  const [newEquipo, setNewEquipo] = useState('');
  const [userBubble, setUserBubble] = useState(false);

  const perPage = 4;
  const filtered = mockEquipos.filter(e =>
    e.nombre.toLowerCase().includes(search.toLowerCase()) ||
    e.codigo.toLowerCase().includes(search.toLowerCase())
  );
  const totalPages = Math.ceil(filtered.length / perPage);
  const paginated = filtered.slice((page - 1) * perPage, page * perPage);

  const bg = darkMode ? 'bg-gray-900' : 'bg-gray-100';
  const cardBg = darkMode ? 'bg-white bg-opacity-5 border-white border-opacity-10' : 'bg-white border-gray-200';
  const text = darkMode ? 'text-white' : 'text-gray-900';
  const subtext = darkMode ? 'text-gray-400' : 'text-gray-500';
  const sidebarBg = darkMode ? 'bg-gray-900 border-white border-opacity-10' : 'bg-white border-gray-200';
  const navbarBg = darkMode ? 'bg-gray-900 border-white border-opacity-10' : 'bg-white border-gray-200';
  const inputBg = darkMode ? 'bg-white bg-opacity-10 border-white border-opacity-10 text-white placeholder-gray-500' : 'bg-gray-100 border-gray-300 text-gray-900 placeholder-gray-400';

  return (
    <div className={`min-h-screen flex flex-col ${bg} ${text} transition-colors duration-300`} style={{fontFamily:'system-ui,sans-serif'}}>

      {/* NAVBAR */}
      <header className={`flex items-center h-14 px-4 border-b ${navbarBg} z-20 relative`}>
        {/* Sidebar toggle */}
        <button onClick={() => setSidebarOpen(!sidebarOpen)}
          className="w-9 h-9 flex flex-col items-center justify-center gap-1.5 rounded-lg hover:bg-white hover:bg-opacity-10 transition mr-3">
          <span className={`block h-0.5 w-5 ${darkMode ? 'bg-white' : 'bg-gray-700'} transition-all`}></span>
          <span className={`block h-0.5 ${sidebarOpen ? 'w-5' : 'w-3'} ${darkMode ? 'bg-white' : 'bg-gray-700'} transition-all`}></span>
          <span className={`block h-0.5 w-5 ${darkMode ? 'bg-white' : 'bg-gray-700'} transition-all`}></span>
        </button>

        {/* Logo */}
        <div className="flex items-center gap-2 mr-6">
          <div className="w-7 h-7 bg-indigo-600 rounded-lg flex items-center justify-center">
            <svg className="w-4 h-4 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 10V3L4 14h7v7l9-11h-7z" />
            </svg>
          </div>
          <span className="font-bold text-sm hidden sm:block">TaskFlow</span>
        </div>

        {/* Search */}
        <div className="flex-1 max-w-xl relative">
          <span className="absolute inset-y-0 left-3 flex items-center text-gray-400">
            <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
            </svg>
          </span>
          <input value={search} onChange={e => { setSearch(e.target.value); setPage(1); }}
            placeholder="Buscar equipos..."
            className={`w-full border rounded-xl pl-9 pr-4 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 transition ${inputBg}`} />
        </div>

        <div className="flex items-center gap-2 ml-auto">
          {/* Dark/Light toggle */}
          <button onClick={() => setDarkMode(!darkMode)}
            className={`w-9 h-9 rounded-lg flex items-center justify-center hover:bg-white hover:bg-opacity-10 transition ${subtext}`}>
            {darkMode ? (
              <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.8} d="M12 3v1m0 16v1m9-9h-1M4 12H3m15.364-6.364l-.707.707M6.343 17.657l-.707.707M17.657 17.657l-.707-.707M6.343 6.343l-.707-.707M16 12a4 4 0 11-8 0 4 4 0 018 0z" />
              </svg>
            ) : (
              <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.8} d="M20.354 15.354A9 9 0 018.646 3.646 9.003 9.003 0 0012 21a9.003 9.003 0 008.354-5.646z" />
              </svg>
            )}
          </button>

          {/* User bubble */}
          <div className="relative">
            <button onClick={() => setUserBubble(!userBubble)}
              className="w-9 h-9 rounded-full bg-indigo-600 flex items-center justify-center text-white font-bold text-sm hover:bg-indigo-500 transition">
              JP
            </button>
            {userBubble && (
              <div className={`absolute right-0 top-11 w-52 rounded-xl shadow-2xl border p-3 z-50 ${darkMode ? 'bg-gray-800 border-white border-opacity-10' : 'bg-white border-gray-200'}`}>
                <div className="flex items-center gap-3 mb-3 pb-3 border-b border-white border-opacity-10">
                  <div className="w-9 h-9 rounded-full bg-indigo-600 flex items-center justify-center text-white font-bold text-sm">JP</div>
                  <div>
                    <p className={`text-sm font-semibold ${text}`}>Juan Pérez</p>
                    <p className="text-xs text-gray-400">@juanperez</p>
                  </div>
                </div>
                <button className={`w-full text-left text-sm px-2 py-1.5 rounded-lg hover:bg-white hover:bg-opacity-10 transition ${subtext} flex items-center gap-2`}>
                  <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.8} d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"/></svg>
                  Mi perfil
                </button>
                <button className="w-full text-left text-sm px-2 py-1.5 rounded-lg hover:bg-red-500 hover:bg-opacity-10 text-red-400 transition flex items-center gap-2 mt-1">
                  <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.8} d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"/></svg>
                  Cerrar sesión
                </button>
              </div>
            )}
          </div>
        </div>
      </header>

      <div className="flex flex-1 overflow-hidden">

        {/* SIDEBAR */}
        <aside className={`${sidebarOpen ? 'w-52' : 'w-0 overflow-hidden'} transition-all duration-300 border-r ${sidebarBg} flex flex-col py-4 shrink-0`}>
          <nav className="flex flex-col gap-1 px-3">
            {[
              { key: 'equipos', label: 'Mis Equipos', icon: <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.8} d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0z"/></svg> },
              { key: 'actividad', label: 'Resumen Actividad', icon: <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.8} d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z"/></svg> },
              { key: 'config', label: 'Configuración', icon: <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.8} d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z"/><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.8} d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"/></svg> },
            ].map(item => (
              <button key={item.key} onClick={() => setActiveNav(item.key)}
                className={`flex items-center gap-3 px-3 py-2.5 rounded-xl text-sm font-medium transition text-left w-full
                  ${activeNav === item.key
                    ? 'bg-indigo-600 text-white shadow-lg'
                    : `${subtext} hover:bg-white hover:bg-opacity-10`}`}>
                {item.icon}
                {item.label}
              </button>
            ))}
          </nav>

          {/* Stats rápidas */}
          <div className={`mx-3 mt-6 p-3 rounded-xl border ${cardBg}`}>
            <p className={`text-xs font-semibold mb-2 ${subtext}`}>RESUMEN</p>
            <div className="space-y-1.5">
              <div className="flex justify-between text-xs">
                <span className={subtext}>Equipos activos</span>
                <span className="text-indigo-400 font-bold">5</span>
              </div>
              <div className="flex justify-between text-xs">
                <span className={subtext}>Tareas pendientes</span>
                <span className="text-yellow-400 font-bold">30</span>
              </div>
              <div className="flex justify-between text-xs">
                <span className={subtext}>Completadas hoy</span>
                <span className="text-green-400 font-bold">8</span>
              </div>
            </div>
          </div>
        </aside>

        {/* MAIN CONTENT */}
        <main className="flex-1 flex flex-col overflow-hidden">

          {/* Toolbar */}
          <div className="flex items-center justify-between px-6 py-4">
            <div>
              <h2 className={`text-lg font-bold ${text}`}>Mis Equipos</h2>
              <p className={`text-xs ${subtext}`}>{filtered.length} equipos encontrados</p>
            </div>
            <div className="flex items-center gap-3">
              <button onClick={() => setShowJoinModal(true)}
                className="flex items-center gap-2 border border-indigo-500 text-indigo-400 hover:bg-indigo-600 hover:text-white text-sm font-medium px-4 py-2 rounded-xl transition">
                <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M18 9v3m0 0v3m0-3h3m-3 0h-3m-2-5a4 4 0 11-8 0 4 4 0 018 0zM3 20a6 6 0 0112 0v1H3v-1z"/></svg>
                Unirse a un Equipo
              </button>
              <button onClick={() => setShowCreateModal(true)}
                className="flex items-center gap-2 bg-indigo-600 hover:bg-indigo-500 text-white text-sm font-medium px-4 py-2 rounded-xl transition shadow-lg">
                <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 4v16m8-8H4"/></svg>
                Crear Equipo
              </button>
            </div>
          </div>

          {/* Grid de equipos */}
          <div className="flex-1 px-6 overflow-y-auto">
            {paginated.length === 0 ? (
              <div className="flex flex-col items-center justify-center h-48 gap-3">
                <svg className="w-12 h-12 text-gray-600" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.5} d="M9.172 16.172a4 4 0 015.656 0M9 10h.01M15 10h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"/></svg>
                <p className="text-gray-500 text-sm">No se encontraron equipos</p>
              </div>
            ) : (
              <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
                {paginated.map(equipo => (
                  <div key={equipo.id}
                    className={`border rounded-2xl p-5 cursor-pointer hover:border-indigo-500 hover:shadow-lg hover:shadow-indigo-500/10 transition group ${cardBg}`}>
                    {/* Header card */}
                    <div className="flex items-start justify-between mb-4">
                      <div className="w-10 h-10 bg-indigo-600 bg-opacity-20 rounded-xl flex items-center justify-center group-hover:bg-indigo-600 transition">
                        <svg className="w-5 h-5 text-indigo-400 group-hover:text-white transition" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.8} d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0z"/>
                        </svg>
                      </div>
                      <span className={`text-xs px-2 py-0.5 rounded-full font-medium ${equipo.activo ? 'bg-green-500 bg-opacity-20 text-green-400' : 'bg-gray-500 bg-opacity-20 text-gray-400'}`}>
                        {equipo.activo ? 'Activo' : 'Inactivo'}
                      </span>
                    </div>

                    {/* Info */}
                    <h3 className={`font-semibold text-sm mb-1 ${text}`}>{equipo.nombre}</h3>
                    <p className={`text-xs mb-4 font-mono ${subtext}`}>{equipo.codigo}</p>

                    {/* Stats */}
                    <div className={`flex gap-3 pt-3 border-t ${darkMode ? 'border-white border-opacity-5' : 'border-gray-100'}`}>
                      <div className="flex-1 text-center">
                        <p className={`text-lg font-bold text-indigo-400`}>{equipo.maps}</p>
                        <p className={`text-xs ${subtext}`}>Etapas</p>
                      </div>
                      <div className={`w-px ${darkMode ? 'bg-white bg-opacity-10' : 'bg-gray-200'}`}></div>
                      <div className="flex-1 text-center">
                        <p className={`text-lg font-bold text-yellow-400`}>{equipo.pendientes}</p>
                        <p className={`text-xs ${subtext}`}>Pendientes</p>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            )}
          </div>

          {/* PAGINACIÓN */}
          <div className={`flex items-center justify-center gap-2 py-4 border-t ${darkMode ? 'border-white border-opacity-10' : 'border-gray-200'}`}>
            <button onClick={() => setPage(p => Math.max(1, p - 1))} disabled={page === 1}
              className={`w-8 h-8 rounded-lg flex items-center justify-center transition text-sm
                ${page === 1 ? 'opacity-30 cursor-not-allowed' : 'hover:bg-white hover:bg-opacity-10'} ${subtext}`}>
              <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 19l-7-7 7-7"/></svg>
            </button>
            {Array.from({ length: totalPages }, (_, i) => i + 1).map(p => (
              <button key={p} onClick={() => setPage(p)}
                className={`w-8 h-8 rounded-lg text-sm font-medium transition
                  ${p === page ? 'bg-indigo-600 text-white' : `${subtext} hover:bg-white hover:bg-opacity-10`}`}>
                {p}
              </button>
            ))}
            <button onClick={() => setPage(p => Math.min(totalPages, p + 1))} disabled={page === totalPages}
              className={`w-8 h-8 rounded-lg flex items-center justify-center transition text-sm
                ${page === totalPages ? 'opacity-30 cursor-not-allowed' : 'hover:bg-white hover:bg-opacity-10'} ${subtext}`}>
              <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5l7 7-7 7"/></svg>
            </button>
          </div>
        </main>
      </div>

      {/* MODAL: Unirse a equipo */}
      {showJoinModal && (
        <div className="fixed inset-0 bg-black bg-opacity-60 flex items-center justify-center z-50 px-4">
          <div className={`w-full max-w-sm rounded-2xl shadow-2xl p-6 border ${darkMode ? 'bg-gray-800 border-white border-opacity-10' : 'bg-white border-gray-200'}`}>
            <h3 className={`text-lg font-bold mb-1 ${text}`}>Unirse a un Equipo</h3>
            <p className={`text-sm mb-4 ${subtext}`}>Ingresa el código de identificación del equipo.</p>
            <input value={joinCode} onChange={e => setJoinCode(e.target.value)}
              placeholder="Ej: FE-001"
              className={`w-full border rounded-xl px-4 py-2.5 text-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 mb-4 ${inputBg}`} />
            <div className="flex gap-3">
              <button onClick={() => setShowJoinModal(false)}
                className={`flex-1 py-2.5 rounded-xl text-sm font-medium border transition ${darkMode ? 'border-white border-opacity-10 text-gray-400 hover:bg-white hover:bg-opacity-5' : 'border-gray-200 text-gray-500 hover:bg-gray-50'}`}>
                Cancelar
              </button>
              <button onClick={() => setShowJoinModal(false)}
                className="flex-1 bg-indigo-600 hover:bg-indigo-500 text-white py-2.5 rounded-xl text-sm font-medium transition">
                Unirse
              </button>
            </div>
          </div>
        </div>
      )}

      {/* MODAL: Crear equipo */}
      {showCreateModal && (
        <div className="fixed inset-0 bg-black bg-opacity-60 flex items-center justify-center z-50 px-4">
          <div className={`w-full max-w-sm rounded-2xl shadow-2xl p-6 border ${darkMode ? 'bg-gray-800 border-white border-opacity-10' : 'bg-white border-gray-200'}`}>
            <h3 className={`text-lg font-bold mb-1 ${text}`}>Crear Equipo</h3>
            <p className={`text-sm mb-4 ${subtext}`}>Dale un nombre a tu nuevo equipo.</p>
            <input value={newEquipo} onChange={e => setNewEquipo(e.target.value)}
              placeholder="Nombre del equipo"
              className={`w-full border rounded-xl px-4 py-2.5 text-sm focus:outline-none focus:ring-2 focus:ring-indigo-500 mb-4 ${inputBg}`} />
            <div className="flex gap-3">
              <button onClick={() => setShowCreateModal(false)}
                className={`flex-1 py-2.5 rounded-xl text-sm font-medium border transition ${darkMode ? 'border-white border-opacity-10 text-gray-400 hover:bg-white hover:bg-opacity-5' : 'border-gray-200 text-gray-500 hover:bg-gray-50'}`}>
                Cancelar
              </button>
              <button onClick={() => setShowCreateModal(false)}
                className="flex-1 bg-indigo-600 hover:bg-indigo-500 text-white py-2.5 rounded-xl text-sm font-medium transition">
                Crear
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}