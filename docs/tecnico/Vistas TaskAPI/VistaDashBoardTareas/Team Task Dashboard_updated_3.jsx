const { useState } = React;

// ── Mock Data ──────────────────────────────────────────────
const USUARIOS = [
  { id: 1, nombre: 'Juan Pérez', usuario: 'juanperez', initials: 'JP', color: 'bg-indigo-500' },
  { id: 2, nombre: 'Ana García', usuario: 'anagarcia', initials: 'AG', color: 'bg-pink-500' },
  { id: 3, nombre: 'Carlos López', usuario: 'carloslopez', initials: 'CL', color: 'bg-green-500' },
  { id: 4, nombre: 'María Torres', usuario: 'mariatorres', initials: 'MT', color: 'bg-yellow-500' },
];

const PRIORIDADES = ['Baja', 'Media', 'Alta', 'Crítica'];
const ESTADOS = ['Pendiente', 'En Progreso', 'En Revisión', 'Completada'];

const prioridadStyle = {
  Baja:     'bg-gray-500 bg-opacity-20 text-gray-400',
  Media:    'bg-blue-500 bg-opacity-20 text-blue-400',
  Alta:     'bg-orange-500 bg-opacity-20 text-orange-400',
  Crítica:  'bg-red-500 bg-opacity-20 text-red-400',
};
const estadoStyle = {
  Pendiente:    { pill: 'bg-yellow-500 bg-opacity-20 text-yellow-400', col: 'border-yellow-500' },
  'En Progreso':{ pill: 'bg-blue-500 bg-opacity-20 text-blue-400',   col: 'border-blue-500' },
  'En Revisión':{ pill: 'bg-purple-500 bg-opacity-20 text-purple-400', col: 'border-purple-500' },
  Completada:   { pill: 'bg-green-500 bg-opacity-20 text-green-400',  col: 'border-green-500' },
};

const initMockTareas = [
  { id: 1, nombre: 'Diseñar pantalla de login', descripcion: 'Crear mockup y componente React del login.', asignador: 1, asignado: 2, prioridad: 'Alta', estado: 'Completada', creacion: '2026-03-01' },
  { id: 2, nombre: 'Configurar CI/CD pipeline', descripcion: 'Integrar GitHub Actions con deploy automático.', asignador: 1, asignado: 3, prioridad: 'Crítica', estado: 'En Progreso', creacion: '2026-03-05' },
  { id: 3, nombre: 'Documentar API REST', descripcion: 'Swagger para todos los endpoints del backend.', asignador: 2, asignado: 4, prioridad: 'Media', estado: 'Pendiente', creacion: '2026-03-08' },
  { id: 4, nombre: 'Optimizar queries DB', descripcion: 'Revisar índices y reducir tiempo de respuesta.', asignador: 3, asignado: 1, prioridad: 'Alta', estado: 'En Revisión', creacion: '2026-03-10' },
  { id: 5, nombre: 'Testing unitario módulo auth', descripcion: 'Cobertura mínima del 80% en autenticación.', asignador: 1, asignado: 2, prioridad: 'Media', estado: 'Pendiente', creacion: '2026-03-11' },
  { id: 6, nombre: 'Migración a TypeScript', descripcion: 'Convertir archivos JS críticos a TS.', asignador: 2, asignado: 3, prioridad: 'Baja', estado: 'Pendiente', creacion: '2026-03-12' },
  { id: 7, nombre: 'Revisión de seguridad', descripcion: 'Auditoría de dependencias y vulnerabilidades.', asignador: 1, asignado: 4, prioridad: 'Crítica', estado: 'En Progreso', creacion: '2026-03-13' },
];

const ROL_ACTUAL = 'admin'; // 'admin' | 'member'
const USUARIO_ACTUAL = USUARIOS[0];
const CREADOR_EQUIPO_ID = 1; // Juan Pérez es el creador del equipo

const PERMISOS_DISPONIBLES = [
  { key: 'crear_tarea', label: 'Crear Tareas', categoria: 'Tareas' },
  { key: 'editar_tarea', label: 'Editar Tareas', categoria: 'Tareas' },
  { key: 'eliminar_tarea', label: 'Eliminar Tareas', categoria: 'Tareas' },
  { key: 'asignar_tarea', label: 'Asignar Tareas', categoria: 'Tareas' },
  { key: 'ver_detalles', label: 'Ver Detalles', categoria: 'Tareas' },
  { key: 'gestionar_roles', label: 'Gestionar Roles', categoria: 'Equipo' },
  { key: 'invitar_miembros', label: 'Invitar Miembros', categoria: 'Equipo' },
  { key: 'expulsar_miembros', label: 'Expulsar Miembros', categoria: 'Equipo' },
  { key: 'ver_actividad', label: 'Ver Actividad', categoria: 'Equipo' },
  { key: 'editar_equipo', label: 'Editar Equipo', categoria: 'Equipo' },
];

const rolColorStyle = {
  'Administrador': 'bg-indigo-500 bg-opacity-20 text-indigo-400 border-indigo-500 border-opacity-30',
  'Desarrollador':  'bg-blue-500 bg-opacity-20 text-blue-400 border-blue-500 border-opacity-30',
  'Observador':     'bg-gray-500 bg-opacity-20 text-gray-400 border-gray-500 border-opacity-30',
  'Tester':         'bg-green-500 bg-opacity-20 text-green-400 border-green-500 border-opacity-30',
  'Líder':          'bg-yellow-500 bg-opacity-20 text-yellow-400 border-yellow-500 border-opacity-30',
};

const initRoles = [
  { id: 1, nombre: 'Administrador', descripcion: 'Acceso total al equipo y sus recursos.', permisos: ['crear_tarea','editar_tarea','eliminar_tarea','asignar_tarea','ver_detalles','gestionar_roles','invitar_miembros','expulsar_miembros','ver_actividad','editar_equipo'], esDefault: true },
  { id: 2, nombre: 'Desarrollador', descripcion: 'Puede crear y editar tareas asignadas.', permisos: ['crear_tarea','editar_tarea','asignar_tarea','ver_detalles','ver_actividad'], esDefault: false },
  { id: 3, nombre: 'Tester', descripcion: 'Puede ver y comentar tareas, sin modificar.', permisos: ['ver_detalles','ver_actividad'], esDefault: false },
  { id: 4, nombre: 'Observador', descripcion: 'Solo lectura del tablero y actividad.', permisos: ['ver_actividad'], esDefault: false },
];

const initMiembrosRoles = [
  { usuarioId: 1, rolId: 1 },
  { usuarioId: 2, rolId: 2 },
  { usuarioId: 3, rolId: 2 },
  { usuarioId: 4, rolId: 3 },
];

// ── Helpers ────────────────────────────────────────────────
const Avatar = ({ usuario, size = 'sm' }) => {
  const u = USUARIOS.find(u => u.id === usuario);
  if (!u) return null;
  const s = size === 'sm' ? 'w-6 h-6 text-xs' : 'w-8 h-8 text-sm';
  return (
    <div className={`${s} ${u.color} rounded-full flex items-center justify-center text-white font-bold shrink-0`} title={u.nombre}>
      {u.initials}
    </div>
  );
};

// ── Main App ───────────────────────────────────────────────
function App() {
  const [darkMode, setDarkMode] = useState(true);
  const [sidebarOpen, setSidebarOpen] = useState(true);
  const [activeTab, setActiveTab] = useState('lista');
  const [tareas, setTareas] = useState(initMockTareas);
  const [search, setSearch] = useState('');
  const [userBubble, setUserBubble] = useState(false);

  // Detalle / modal states
  const [modalOpen, setModalOpen] = useState(false);
  const [editingTarea, setEditingTarea] = useState(null);
  const [assignModal, setAssignModal] = useState(null); // tarea id
  const [assignTarget, setAssignTarget] = useState('');
  const [deleteConfirm, setDeleteConfirm] = useState(null);

  // ── Roles state ──
  const [roles, setRoles] = useState(initRoles);
  const [miembrosRoles, setMiembrosRoles] = useState(initMiembrosRoles);
  const [rolModal, setRolModal] = useState(false);
  const [editingRol, setEditingRol] = useState(null);
  const [deleteRolConfirm, setDeleteRolConfirm] = useState(null);
  const [assignRolModal, setAssignRolModal] = useState(null); // usuarioId
  const [assignRolTarget, setAssignRolTarget] = useState('');
  const emptyRolForm = { nombre: '', descripcion: '', permisos: [] };
  const [rolForm, setRolForm] = useState(emptyRolForm);
  const [rolFormErrors, setRolFormErrors] = useState({});
  const [rolesSubTab, setRolesSubTab] = useState('roles');

  // ── Miembros state ──
  const [miembros, setMiembros] = useState(USUARIOS.map(u => ({ ...u })));
  const [kickConfirm, setKickConfirm] = useState(null); // usuarioId
  const [memberSearch, setMemberSearch] = useState('');

  const canManageRoles = USUARIO_ACTUAL.id === CREADOR_EQUIPO_ID || ROL_ACTUAL === 'admin';
  const canManageMembers = USUARIO_ACTUAL.id === CREADOR_EQUIPO_ID || ROL_ACTUAL === 'admin';

  const kickMember = (id) => {
    setMiembros(prev => prev.filter(m => m.id !== id));
    setMiembrosRoles(prev => prev.filter(mr => mr.usuarioId !== id));
    setKickConfirm(null);
  };

  const openCreateRol = () => { setEditingRol(null); setRolForm(emptyRolForm); setRolFormErrors({}); setRolModal(true); };
  const openEditRol = (r) => { setEditingRol(r.id); setRolForm({ nombre: r.nombre, descripcion: r.descripcion, permisos: [...r.permisos] }); setRolFormErrors({}); setRolModal(true); };
  const validateRolForm = () => { const e = {}; if (!rolForm.nombre.trim()) e.nombre = 'Requerido'; return e; };
  const saveRolForm = () => {
    const e = validateRolForm();
    if (Object.keys(e).length) { setRolFormErrors(e); return; }
    if (editingRol) {
      setRoles(prev => prev.map(r => r.id === editingRol ? { ...r, ...rolForm } : r));
    } else {
      setRoles(prev => [...prev, { ...rolForm, id: Date.now(), esDefault: false }]);
    }
    setRolModal(false);
  };
  const deleteRol = (id) => { setRoles(prev => prev.filter(r => r.id !== id)); setMiembrosRoles(prev => prev.filter(mr => mr.rolId !== id)); setDeleteRolConfirm(null); };
  const togglePermiso = (key) => setRolForm(prev => ({ ...prev, permisos: prev.permisos.includes(key) ? prev.permisos.filter(p => p !== key) : [...prev.permisos, key] }));
  const saveAssignRol = () => {
    if (!assignRolTarget) return;
    setMiembrosRoles(prev => prev.map(mr => mr.usuarioId === assignRolModal ? { ...mr, rolId: parseInt(assignRolTarget) } : mr));
    setAssignRolModal(null); setAssignRolTarget('');
  };
  const getRolDeUsuario = (usuarioId) => { const mr = miembrosRoles.find(mr => mr.usuarioId === usuarioId); return mr ? roles.find(r => r.id === mr.rolId) : null; };

  // Form state
  const emptyForm = { nombre: '', descripcion: '', asignador: USUARIO_ACTUAL.id, asignado: '', prioridad: 'Media', estado: 'Pendiente' };
  const [form, setForm] = useState(emptyForm);
  const [formErrors, setFormErrors] = useState({});

  // Theme tokens
  const bg       = darkMode ? 'bg-gray-900' : 'bg-gray-100';
  const cardBg   = darkMode ? 'bg-white bg-opacity-5 border border-white border-opacity-10' : 'bg-white border border-gray-200';
  const text      = darkMode ? 'text-white' : 'text-gray-900';
  const subtext   = darkMode ? 'text-gray-400' : 'text-gray-500';
  const navBg     = darkMode ? 'bg-gray-900 border-white border-opacity-10' : 'bg-white border-gray-200';
  const inputCls  = darkMode
    ? 'bg-white bg-opacity-10 border border-white border-opacity-10 text-white placeholder-gray-500 focus:ring-indigo-500'
    : 'bg-gray-100 border border-gray-300 text-gray-900 placeholder-gray-400 focus:ring-indigo-500';
  const divider   = darkMode ? 'border-white border-opacity-10' : 'border-gray-200';

  const filteredTareas = tareas.filter(t =>
    t.nombre.toLowerCase().includes(search.toLowerCase()) ||
    t.descripcion.toLowerCase().includes(search.toLowerCase())
  );

  // ── CRUD ──
  const openCreate = () => {
    setEditingTarea(null);
    setForm(emptyForm);
    setFormErrors({});
    setModalOpen(true);
  };
  const openEdit = (t) => {
    setEditingTarea(t.id);
    setForm({ nombre: t.nombre, descripcion: t.descripcion, asignador: t.asignador, asignado: t.asignado, prioridad: t.prioridad, estado: t.estado });
    setFormErrors({});
    setModalOpen(true);
  };
  const validateForm = () => {
    const e = {};
    if (!form.nombre.trim()) e.nombre = 'Requerido';
    if (!form.descripcion.trim()) e.descripcion = 'Requerido';
    if (!form.asignado) e.asignado = 'Selecciona un usuario';
    return e;
  };
  const saveForm = () => {
    const e = validateForm();
    if (Object.keys(e).length) { setFormErrors(e); return; }
    if (editingTarea) {
      setTareas(prev => prev.map(t => t.id === editingTarea ? { ...t, ...form } : t));
    } else {
      setTareas(prev => [...prev, { ...form, id: Date.now(), creacion: new Date().toISOString().slice(0,10) }]);
    }
    setModalOpen(false);
  };
  const deleteTarea = (id) => { setTareas(prev => prev.filter(t => t.id !== id)); setDeleteConfirm(null); };
  const saveAssign = () => {
    if (!assignTarget) return;
    setTareas(prev => prev.map(t => t.id === assignModal ? { ...t, asignado: parseInt(assignTarget) } : t));
    setAssignModal(null); setAssignTarget('');
  };
  const moveEstado = (id, estado) => setTareas(prev => prev.map(t => t.id === id ? { ...t, estado } : t));

  const canEdit = ROL_ACTUAL === 'admin';

  // ── TABS ──────────────────────────────────────────────────
  const tabs = [
    { key: 'lista', label: 'Lista Tareas' },
    { key: 'tablero', label: 'Tablero' },
    ...(canEdit ? [{ key: 'detalles', label: 'Detalles Tarea' }] : []),
    ...(canManageRoles ? [{ key: 'roles', label: 'Roles del Equipo' }] : []),
    ...(canManageMembers ? [{ key: 'miembros', label: 'Gestión de Miembros' }] : []),
  ];

  return (
    <div className={`min-h-screen flex flex-col ${bg} ${text} transition-colors duration-300`} style={{fontFamily:'system-ui,sans-serif'}}>

      {/* ── NAVBAR ── */}
      <header className={`flex items-center h-14 px-4 border-b ${navBg} z-20 relative shrink-0`}>
        <button onClick={() => setSidebarOpen(!sidebarOpen)}
          className="w-9 h-9 flex flex-col items-center justify-center gap-1.5 rounded-lg hover:bg-white hover:bg-opacity-10 transition mr-3">
          {[true, sidebarOpen, true].map((full, i) => (
            <span key={i} className={`block h-0.5 ${full ? 'w-5' : 'w-3'} ${darkMode ? 'bg-white' : 'bg-gray-700'} transition-all`}></span>
          ))}
        </button>
        <div className="flex items-center gap-2 mr-6">
          <div className="w-7 h-7 bg-indigo-600 rounded-lg flex items-center justify-center">
            <svg className="w-4 h-4 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 10V3L4 14h7v7l9-11h-7z"/>
            </svg>
          </div>
          <span className="font-bold text-sm hidden sm:block">TaskFlow</span>
        </div>
        <div className="flex-1 max-w-xl relative">
          <span className="absolute inset-y-0 left-3 flex items-center text-gray-400">
            <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"/></svg>
          </span>
          <input value={search} onChange={e => setSearch(e.target.value)} placeholder="Buscar tareas..."
            className={`w-full rounded-xl pl-9 pr-4 py-2 text-sm focus:outline-none focus:ring-2 transition ${inputCls}`}/>
        </div>
        <div className="flex items-center gap-2 ml-auto">
          <button onClick={() => setDarkMode(!darkMode)}
            className={`w-9 h-9 rounded-lg flex items-center justify-center hover:bg-white hover:bg-opacity-10 transition ${subtext}`}>
            {darkMode
              ? <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.8} d="M12 3v1m0 16v1m9-9h-1M4 12H3m15.364-6.364l-.707.707M6.343 17.657l-.707.707M17.657 17.657l-.707-.707M6.343 6.343l-.707-.707M16 12a4 4 0 11-8 0 4 4 0 018 0z"/></svg>
              : <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.8} d="M20.354 15.354A9 9 0 018.646 3.646 9.003 9.003 0 0012 21a9.003 9.003 0 008.354-5.646z"/></svg>
            }
          </button>
          <div className="relative">
            <button onClick={() => setUserBubble(!userBubble)}
              className="w-9 h-9 rounded-full bg-indigo-600 flex items-center justify-center text-white font-bold text-sm hover:bg-indigo-500 transition">
              JP
            </button>
            {userBubble && (
              <div className={`absolute right-0 top-11 w-52 rounded-xl shadow-2xl border p-3 z-50 ${darkMode ? 'bg-gray-800 border-white border-opacity-10' : 'bg-white border-gray-200'}`}>
                <div className={`flex items-center gap-3 mb-3 pb-3 border-b ${divider}`}>
                  <div className="w-9 h-9 rounded-full bg-indigo-600 flex items-center justify-center text-white font-bold text-sm">JP</div>
                  <div>
                    <p className={`text-sm font-semibold ${text}`}>Juan Pérez</p>
                    <p className="text-xs text-indigo-400 font-medium">Admin</p>
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

        {/* ── SIDEBAR ── */}
        <aside className={`${sidebarOpen ? 'w-52' : 'w-0 overflow-hidden'} transition-all duration-300 border-r ${navBg} flex flex-col py-4 shrink-0`}>
          <div className="px-3 mb-4">
            <button className={`flex items-center gap-2 text-xs ${subtext} hover:text-indigo-400 transition mb-3`}>
              <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 19l-7-7 7-7"/></svg>
              Volver a Equipos
            </button>
            <div className={`p-3 rounded-xl ${cardBg}`}>
              <div className="flex items-center gap-2 mb-1">
                <div className="w-7 h-7 bg-indigo-600 rounded-lg flex items-center justify-center">
                  <svg className="w-4 h-4 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.8} d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0z"/></svg>
                </div>
                <div>
                  <p className={`text-xs font-bold ${text}`}>Frontend Squad</p>
                  <p className="text-xs text-indigo-400">FE-001</p>
                </div>
              </div>
            </div>
          </div>
          <nav className="flex flex-col gap-1 px-3">
            {[
              { key: 'equipos', label: 'Mis Equipos', icon: <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.8} d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0z"/></svg> },
              { key: 'actividad', label: 'Resumen Actividad', icon: <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.8} d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z"/></svg> },
              { key: 'config', label: 'Configuración', icon: <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.8} d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z"/><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.8} d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"/></svg> },
            ].map(item => (
              <button key={item.key}
                className={`flex items-center gap-3 px-3 py-2.5 rounded-xl text-sm font-medium transition text-left w-full ${subtext} hover:bg-white hover:bg-opacity-10`}>
                {item.icon}{item.label}
              </button>
            ))}
          </nav>
          {/* Miembros */}
          <div className={`mx-3 mt-5 p-3 rounded-xl ${cardBg}`}>
            <p className={`text-xs font-semibold mb-2 ${subtext}`}>MIEMBROS</p>
            <div className="space-y-2">
              {USUARIOS.map(u => (
                <div key={u.id} className="flex items-center gap-2">
                  <div className={`w-6 h-6 ${u.color} rounded-full flex items-center justify-center text-white text-xs font-bold`}>{u.initials}</div>
                  <span className={`text-xs ${subtext}`}>{u.nombre}</span>
                </div>
              ))}
            </div>
          </div>
        </aside>

        {/* ── CONTENT ── */}
        <main className="flex-1 flex flex-col overflow-hidden">
          <div className="px-6 pt-5 pb-0 shrink-0">
            {/* Equipo header */}
            <div className="flex items-center justify-between mb-4">
              <div>
                <h1 className={`text-xl font-bold ${text}`}>Frontend Squad</h1>
                <p className={`text-xs ${subtext}`}>FE-001 · {tareas.length} tareas en total</p>
              </div>
              {canEdit && (
                <button onClick={openCreate}
                  className="flex items-center gap-2 bg-indigo-600 hover:bg-indigo-500 text-white text-sm font-medium px-4 py-2 rounded-xl transition shadow-lg">
                  <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 4v16m8-8H4"/></svg>
                  Nueva Tarea
                </button>
              )}
            </div>

            {/* Tabs */}
            <div className={`flex gap-1 border-b ${divider}`}>
              {tabs.map(tab => (
                <button key={tab.key} onClick={() => setActiveTab(tab.key)}
                  className={`px-4 py-2.5 text-sm font-medium transition relative
                    ${activeTab === tab.key
                      ? `text-indigo-400 after:absolute after:bottom-0 after:left-0 after:right-0 after:h-0.5 after:bg-indigo-500 after:rounded-t`
                      : `${subtext} hover:text-white`}`}>
                  {tab.label}
                  {(tab.key === 'detalles' || tab.key === 'roles' || tab.key === 'miembros') && (
                    <span className="ml-1.5 text-xs bg-indigo-600 bg-opacity-30 text-indigo-400 px-1.5 py-0.5 rounded-full">
                      {tab.key === 'roles' ? '👑 Creador' : tab.key === 'miembros' ? '🛡️ Auth' : 'Admin'}
                    </span>
                  )}
                </button>
              ))}
            </div>
          </div>

          {/* ── TAB: LISTA ── */}
          {activeTab === 'lista' && (
            <div className="flex-1 overflow-auto px-6 py-4">
              <div className={`rounded-2xl overflow-hidden border ${darkMode ? 'border-white border-opacity-10' : 'border-gray-200'}`}>
                <table className="w-full text-sm">
                  <thead>
                    <tr className={`${darkMode ? 'bg-white bg-opacity-5' : 'bg-gray-50'} text-xs uppercase tracking-wide`}>
                      {['Nombre', 'Descripción', 'Asignador', 'Asignado', 'Prioridad', 'Estado', 'Acciones'].map(h => (
                        <th key={h} className={`px-4 py-3 text-left font-semibold ${subtext}`}>{h}</th>
                      ))}
                    </tr>
                  </thead>
                  <tbody>
                    {filteredTareas.map((t, i) => (
                      <tr key={t.id}
                        className={`border-t ${divider} transition hover:bg-white hover:bg-opacity-5 ${i % 2 === 0 ? '' : darkMode ? 'bg-white bg-opacity-2' : 'bg-gray-50'}`}>
                        <td className={`px-4 py-3 font-medium ${text} max-w-xs`}>
                          <p className="truncate">{t.nombre}</p>
                        </td>
                        <td className={`px-4 py-3 ${subtext} max-w-xs`}>
                          <p className="truncate text-xs">{t.descripcion}</p>
                        </td>
                        <td className="px-4 py-3"><Avatar usuario={t.asignador} /></td>
                        <td className="px-4 py-3">
                          <div className="flex items-center gap-2">
                            <Avatar usuario={t.asignado} />
                            {canEdit && (
                              <button onClick={() => { setAssignModal(t.id); setAssignTarget(t.asignado); }}
                                className="text-indigo-400 hover:text-indigo-300 transition" title="Reasignar">
                                <svg className="w-3.5 h-3.5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15.232 5.232l3.536 3.536m-2.036-5.036a2.5 2.5 0 113.536 3.536L6.5 21.036H3v-3.572L16.732 3.732z"/></svg>
                              </button>
                            )}
                          </div>
                        </td>
                        <td className="px-4 py-3">
                          <span className={`text-xs px-2 py-0.5 rounded-full font-medium ${prioridadStyle[t.prioridad]}`}>{t.prioridad}</span>
                        </td>
                        <td className="px-4 py-3">
                          <span className={`text-xs px-2 py-0.5 rounded-full font-medium ${estadoStyle[t.estado].pill}`}>{t.estado}</span>
                        </td>
                        <td className="px-4 py-3">
                          <div className="flex items-center gap-2">
                            {canEdit && (
                              <>
                                <button onClick={() => openEdit(t)} className="text-gray-400 hover:text-indigo-400 transition" title="Editar">
                                  <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.8} d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"/></svg>
                                </button>
                                <button onClick={() => setDeleteConfirm(t.id)} className="text-gray-400 hover:text-red-400 transition" title="Eliminar">
                                  <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.8} d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"/></svg>
                                </button>
                              </>
                            )}
                          </div>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
                {filteredTareas.length === 0 && (
                  <div className="flex flex-col items-center justify-center py-12 gap-2">
                    <svg className="w-10 h-10 text-gray-600" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.5} d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2"/></svg>
                    <p className="text-gray-500 text-sm">No hay tareas</p>
                  </div>
                )}
              </div>
            </div>
          )}

          {/* ── TAB: TABLERO ── */}
          {activeTab === 'tablero' && (
            <div className="flex-1 overflow-auto px-6 py-4">
              <div className="flex gap-4 h-full min-w-max">
                {ESTADOS.map(estado => {
                  const col = estadoStyle[estado];
                  const colTareas = filteredTareas.filter(t => t.estado === estado);
                  return (
                    <div key={estado} className="w-64 flex flex-col shrink-0">
                      {/* Column header */}
                      <div className={`flex items-center justify-between mb-3 px-1`}>
                        <div className="flex items-center gap-2">
                          <div className={`w-2.5 h-2.5 rounded-full ${col.pill.split(' ')[0]}`}></div>
                          <span className={`text-sm font-semibold ${text}`}>{estado}</span>
                        </div>
                        <span className={`text-xs px-2 py-0.5 rounded-full ${col.pill}`}>{colTareas.length}</span>
                      </div>
                      {/* Cards */}
                      <div className={`flex-1 rounded-2xl p-2 border-t-2 ${col.col} ${darkMode ? 'bg-white bg-opacity-3' : 'bg-gray-50'} space-y-2 overflow-y-auto`}
                        style={{minHeight:'200px', background: darkMode ? 'rgba(255,255,255,0.02)' : undefined}}>
                        {colTareas.map(t => (
                          <div key={t.id} className={`rounded-xl p-3 border ${cardBg} hover:border-indigo-500 transition cursor-pointer group`}>
                            <div className="flex items-start justify-between gap-2 mb-2">
                              <p className={`text-xs font-semibold ${text} leading-snug`}>{t.nombre}</p>
                              {canEdit && (
                                <button onClick={() => openEdit(t)} className="opacity-0 group-hover:opacity-100 transition text-gray-400 hover:text-indigo-400 shrink-0">
                                  <svg className="w-3.5 h-3.5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15.232 5.232l3.536 3.536m-2.036-5.036a2.5 2.5 0 113.536 3.536L6.5 21.036H3v-3.572L16.732 3.732z"/></svg>
                                </button>
                              )}
                            </div>
                            <p className={`text-xs ${subtext} mb-3 line-clamp-2`}>{t.descripcion}</p>
                            <div className="flex items-center justify-between">
                              <span className={`text-xs px-1.5 py-0.5 rounded-full ${prioridadStyle[t.prioridad]}`}>{t.prioridad}</span>
                              <div className="flex items-center gap-1">
                                <Avatar usuario={t.asignado} size="sm" />
                              </div>
                            </div>
                            {/* Move buttons */}
                            {canEdit && (
                              <div className="flex gap-1 mt-2 opacity-0 group-hover:opacity-100 transition">
                                {ESTADOS.filter(e => e !== estado).map(e => (
                                  <button key={e} onClick={() => moveEstado(t.id, e)}
                                    className={`text-xs px-1.5 py-0.5 rounded-lg ${estadoStyle[e].pill} hover:opacity-80 transition`}>
                                    → {e.split(' ')[0]}
                                  </button>
                                ))}
                              </div>
                            )}
                          </div>
                        ))}
                        {colTareas.length === 0 && (
                          <div className="flex items-center justify-center h-20">
                            <p className={`text-xs ${subtext} opacity-50`}>Sin tareas</p>
                          </div>
                        )}
                      </div>
                    </div>
                  );
                })}
              </div>
            </div>
          )}

          {/* ── TAB: ROLES (Creador only) ── */}
          {activeTab === 'roles' && canManageRoles && (
            <div className="flex-1 overflow-auto px-6 py-4">
              <div className="max-w-3xl">
                <div className="flex items-center justify-between mb-4">
                  <div>
                    <h3 className={`font-semibold ${text}`}>Roles del Equipo</h3>
                    <p className={`text-xs ${subtext}`}>{roles.length} roles definidos</p>
                  </div>
                  <button onClick={openCreateRol}
                    className="flex items-center gap-2 bg-indigo-600 hover:bg-indigo-500 text-white text-sm font-medium px-4 py-2 rounded-xl transition">
                    <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 4v16m8-8H4"/></svg>
                    Nuevo Rol
                  </button>
                </div>
                <div className="space-y-3">
                  {roles.map(r => {
                    const colorCls = rolColorStyle[r.nombre] || 'bg-indigo-500 bg-opacity-20 text-indigo-400 border-indigo-500 border-opacity-30';
                    return (
                      <div key={r.id} className={`rounded-2xl p-4 border ${cardBg} hover:border-indigo-500 transition group`}>
                        <div className="flex items-start justify-between gap-3">
                          <div className="flex items-center gap-3">
                            <div className={`px-2.5 py-1 rounded-lg text-xs font-bold border ${colorCls}`}>{r.nombre}</div>
                            {r.esDefault && <span className="text-xs bg-yellow-500 bg-opacity-20 text-yellow-400 px-2 py-0.5 rounded-full">Default</span>}
                          </div>
                          <div className="flex items-center gap-2 opacity-0 group-hover:opacity-100 transition">
                            <button onClick={() => openEditRol(r)}
                              className="text-gray-400 hover:text-indigo-400 transition p-1.5 rounded-lg hover:bg-indigo-600 hover:bg-opacity-10" title="Editar">
                              <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.8} d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"/></svg>
                            </button>
                            {!r.esDefault && (
                              <button onClick={() => setDeleteRolConfirm(r.id)}
                                className="text-gray-400 hover:text-red-400 transition p-1.5 rounded-lg hover:bg-red-600 hover:bg-opacity-10" title="Eliminar">
                                <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.8} d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"/></svg>
                              </button>
                            )}
                          </div>
                        </div>
                        <p className={`text-xs ${subtext} mt-2 mb-3`}>{r.descripcion}</p>
                        <div className="space-y-2">
                          {['Tareas','Equipo'].map(cat => {
                            const permisosCat = PERMISOS_DISPONIBLES.filter(p => p.categoria === cat && r.permisos.includes(p.key));
                            if (!permisosCat.length) return null;
                            return (
                              <div key={cat}>
                                <p className={`text-xs font-semibold mb-1 ${subtext} uppercase tracking-wide`}>{cat}</p>
                                <div className="flex flex-wrap gap-1.5">
                                  {permisosCat.map(p => (
                                    <span key={p.key} className="text-xs bg-indigo-600 bg-opacity-15 text-indigo-400 px-2 py-0.5 rounded-full border border-indigo-500 border-opacity-20">
                                      {p.label}
                                    </span>
                                  ))}
                                </div>
                              </div>
                            );
                          })}
                          {r.permisos.length === 0 && <p className={`text-xs ${subtext} opacity-50`}>Sin permisos asignados</p>}
                        </div>
                        <div className={`mt-3 pt-3 border-t ${divider} flex items-center gap-2`}>
                          <svg className="w-3.5 h-3.5 text-gray-500" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0z"/></svg>
                          <span className={`text-xs ${subtext}`}>{miembrosRoles.filter(mr => mr.rolId === r.id).length} miembro(s) con este rol</span>
                        </div>
                      </div>
                    );
                  })}
                </div>
              </div>
            </div>
          )}

          {/* ── TAB: MIEMBROS (Auth only) ── */}
          {activeTab === 'miembros' && canManageMembers && (
            <div className="flex-1 overflow-auto px-6 py-4">
              <div className="max-w-3xl">
                {/* Header */}
                <div className="flex items-center justify-between mb-4">
                  <div>
                    <h3 className={`font-semibold ${text}`}>Gestión de Miembros</h3>
                    <p className={`text-xs ${subtext}`}>{miembros.length} miembro(s) en el equipo</p>
                  </div>
                  <div className="relative">
                    <span className="absolute inset-y-0 left-3 flex items-center text-gray-400">
                      <svg className="w-3.5 h-3.5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"/></svg>
                    </span>
                    <input value={memberSearch} onChange={e => setMemberSearch(e.target.value)} placeholder="Buscar miembro..."
                      className={`rounded-xl pl-8 pr-4 py-2 text-sm focus:outline-none focus:ring-2 transition ${inputCls}`} style={{width:'200px'}}/>
                  </div>
                </div>

                {/* Stats row */}
                <div className="grid grid-cols-3 gap-3 mb-5">
                  {[
                    { label: 'Total Miembros', value: miembros.length, icon: '👥', color: 'text-indigo-400' },
                    { label: 'Con Rol Asignado', value: miembros.filter(m => getRolDeUsuario(m.id)).length, icon: '🏷️', color: 'text-green-400' },
                    { label: 'Sin Rol', value: miembros.filter(m => !getRolDeUsuario(m.id)).length, icon: '⚠️', color: 'text-yellow-400' },
                  ].map(s => (
                    <div key={s.label} className={`rounded-xl p-3 border ${cardBg} flex items-center gap-3`}>
                      <span className="text-xl">{s.icon}</span>
                      <div>
                        <p className={`text-lg font-bold ${s.color}`}>{s.value}</p>
                        <p className={`text-xs ${subtext}`}>{s.label}</p>
                      </div>
                    </div>
                  ))}
                </div>

                {/* Members table */}
                <div className={`rounded-2xl overflow-hidden border ${darkMode ? 'border-white border-opacity-10' : 'border-gray-200'}`}>
                  <table className="w-full text-sm">
                    <thead>
                      <tr className={`${darkMode ? 'bg-white bg-opacity-5' : 'bg-gray-50'} text-xs uppercase tracking-wide`}>
                        {['Miembro', 'Usuario', 'Rol Actual', 'Permisos', 'Acciones'].map(h => (
                          <th key={h} className={`px-4 py-3 text-left font-semibold ${subtext}`}>{h}</th>
                        ))}
                      </tr>
                    </thead>
                    <tbody>
                      {miembros
                        .filter(u => u.nombre.toLowerCase().includes(memberSearch.toLowerCase()) || u.usuario.toLowerCase().includes(memberSearch.toLowerCase()))
                        .map(u => {
                          const rolUsuario = getRolDeUsuario(u.id);
                          const colorCls = rolUsuario ? (rolColorStyle[rolUsuario.nombre] || 'bg-indigo-500 bg-opacity-20 text-indigo-400 border-indigo-500 border-opacity-30') : 'bg-gray-500 bg-opacity-20 text-gray-400 border-gray-500 border-opacity-30';
                          const esCreador = u.id === CREADOR_EQUIPO_ID;
                          const esSelf = u.id === USUARIO_ACTUAL.id;
                          return (
                            <tr key={u.id} className={`border-t ${divider} transition hover:bg-white hover:bg-opacity-5`}>
                              <td className="px-4 py-3">
                                <div className="flex items-center gap-2.5">
                                  <div className={`w-9 h-9 ${u.color} rounded-full flex items-center justify-center text-white font-bold text-sm shrink-0`}>{u.initials}</div>
                                  <div>
                                    <p className={`text-sm font-medium ${text}`}>{u.nombre}</p>
                                    <div className="flex items-center gap-1.5 mt-0.5">
                                      {esCreador && <span className="text-xs text-yellow-400 font-medium">👑 Creador</span>}
                                      {esSelf && !esCreador && <span className="text-xs text-indigo-400 font-medium">• Tú</span>}
                                    </div>
                                  </div>
                                </div>
                              </td>
                              <td className={`px-4 py-3 text-xs ${subtext}`}>@{u.usuario}</td>
                              <td className="px-4 py-3">
                                {rolUsuario
                                  ? <span className={`text-xs px-2 py-1 rounded-lg font-medium border ${colorCls}`}>{rolUsuario.nombre}</span>
                                  : <span className={`text-xs ${subtext} opacity-50 italic`}>Sin rol</span>}
                              </td>
                              <td className="px-4 py-3">
                                <span className={`text-xs ${subtext}`}>{rolUsuario ? rolUsuario.permisos.length : 0} permisos</span>
                              </td>
                              <td className="px-4 py-3">
                                <div className="flex items-center gap-2">
                                  {!esCreador ? (
                                    <>
                                      <button
                                        onClick={() => { setAssignRolModal(u.id); setAssignRolTarget(miembrosRoles.find(mr => mr.usuarioId === u.id)?.rolId || ''); }}
                                        className="text-xs flex items-center gap-1.5 text-indigo-400 hover:text-indigo-300 transition px-2.5 py-1.5 rounded-lg hover:bg-indigo-600 hover:bg-opacity-10 border border-indigo-500 border-opacity-30">
                                        <svg className="w-3.5 h-3.5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15.232 5.232l3.536 3.536m-2.036-5.036a2.5 2.5 0 113.536 3.536L6.5 21.036H3v-3.572L16.732 3.732z"/></svg>
                                        Rol
                                      </button>
                                      <button
                                        onClick={() => setKickConfirm(u.id)}
                                        className="text-xs flex items-center gap-1.5 text-red-400 hover:text-red-300 transition px-2.5 py-1.5 rounded-lg hover:bg-red-600 hover:bg-opacity-10 border border-red-500 border-opacity-30">
                                        <svg className="w-3.5 h-3.5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 7a4 4 0 11-8 0 4 4 0 018 0zM9 14a6 6 0 00-6 6v1h12v-1a6 6 0 00-6-6zM21 12h-6m3-3l3 3-3 3"/></svg>
                                        Expulsar
                                      </button>
                                    </>
                                  ) : (
                                    <span className={`text-xs ${subtext} opacity-40`}>No modificable</span>
                                  )}
                                </div>
                              </td>
                            </tr>
                          );
                        })}
                    </tbody>
                  </table>
                  {miembros.filter(u => u.nombre.toLowerCase().includes(memberSearch.toLowerCase()) || u.usuario.toLowerCase().includes(memberSearch.toLowerCase())).length === 0 && (
                    <div className="flex flex-col items-center justify-center py-10 gap-2">
                      <svg className="w-8 h-8 text-gray-600" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.5} d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0z"/></svg>
                      <p className={`text-sm ${subtext}`}>No se encontraron miembros</p>
                    </div>
                  )}
                </div>
              </div>
            </div>
          )}

          {/* ── TAB: DETALLES (Admin only) ── */}
          {activeTab === 'detalles' && canEdit && (
            <div className="flex-1 overflow-auto px-6 py-4">
              <div className="max-w-2xl">
                <div className={`rounded-2xl p-5 border ${cardBg} mb-4`}>
                  <div className="flex items-center gap-3 mb-4">
                    <div className="w-8 h-8 bg-indigo-600 bg-opacity-20 rounded-lg flex items-center justify-center">
                      <svg className="w-4 h-4 text-indigo-400" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.8} d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2m-6 9l2 2 4-4"/></svg>
                    </div>
                    <div>
                      <h3 className={`font-semibold ${text}`}>Gestión de Tareas</h3>
                      <p className={`text-xs ${subtext}`}>Solo visible para roles con privilegios de administración</p>
                    </div>
                    <span className="ml-auto text-xs bg-indigo-600 bg-opacity-20 text-indigo-400 px-2 py-1 rounded-full font-medium">🔐 Admin</span>
                  </div>
                  <button onClick={openCreate}
                    className="w-full flex items-center justify-center gap-2 border border-dashed border-indigo-500 border-opacity-50 text-indigo-400 hover:bg-indigo-600 hover:bg-opacity-10 py-3 rounded-xl text-sm font-medium transition">
                    <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 4v16m8-8H4"/></svg>
                    Crear nueva tarea
                  </button>
                </div>

                {/* Lista editable */}
                <div className="space-y-2">
                  {tareas.map(t => (
                    <div key={t.id} className={`flex items-center gap-3 p-3 rounded-xl border ${cardBg} hover:border-indigo-500 transition group`}>
                      <div className={`w-2 h-2 rounded-full shrink-0 ${estadoStyle[t.estado].pill.split(' ')[0]}`}></div>
                      <div className="flex-1 min-w-0">
                        <p className={`text-sm font-medium ${text} truncate`}>{t.nombre}</p>
                        <div className="flex items-center gap-2 mt-0.5">
                          <span className={`text-xs ${prioridadStyle[t.prioridad]} px-1.5 py-0.5 rounded-full`}>{t.prioridad}</span>
                          <span className={`text-xs ${subtext}`}>{t.creacion}</span>
                        </div>
                      </div>
                      <div className="flex items-center gap-2 opacity-0 group-hover:opacity-100 transition">
                        <button onClick={() => { setAssignModal(t.id); setAssignTarget(t.asignado); }}
                          className="text-xs flex items-center gap-1 text-indigo-400 hover:text-indigo-300 transition px-2 py-1 rounded-lg hover:bg-indigo-600 hover:bg-opacity-10">
                          <svg className="w-3.5 h-3.5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"/></svg>
                          Asignar
                        </button>
                        <button onClick={() => openEdit(t)} className="text-gray-400 hover:text-indigo-400 transition">
                          <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.8} d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"/></svg>
                        </button>
                        <button onClick={() => setDeleteConfirm(t.id)} className="text-gray-400 hover:text-red-400 transition">
                          <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.8} d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"/></svg>
                        </button>
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            </div>
          )}
        </main>
      </div>

      {/* ── MODAL: Crear / Editar Tarea ── */}
      {modalOpen && (
        <div className="fixed inset-0 bg-black bg-opacity-70 flex items-center justify-center z-50 px-4">
          <div className={`w-full max-w-lg rounded-2xl shadow-2xl border p-6 ${darkMode ? 'bg-gray-800 border-white border-opacity-10' : 'bg-white border-gray-200'}`}>
            <div className="flex items-center justify-between mb-5">
              <h3 className={`text-lg font-bold ${text}`}>{editingTarea ? 'Editar Tarea' : 'Nueva Tarea'}</h3>
              <button onClick={() => setModalOpen(false)} className={`${subtext} hover:text-white transition`}>
                <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12"/></svg>
              </button>
            </div>
            <div className="space-y-4">
              {/* Nombre */}
              <div>
                <label className={`block text-sm font-medium mb-1.5 ${subtext}`}>Nombre</label>
                <input value={form.nombre} onChange={e => setForm({...form, nombre: e.target.value})}
                  placeholder="Nombre de la tarea"
                  className={`w-full rounded-xl px-4 py-2.5 text-sm focus:outline-none focus:ring-2 transition ${inputCls} ${formErrors.nombre ? 'ring-2 ring-red-500' : ''}`}/>
                {formErrors.nombre && <p className="text-red-400 text-xs mt-1">{formErrors.nombre}</p>}
              </div>
              {/* Descripción */}
              <div>
                <label className={`block text-sm font-medium mb-1.5 ${subtext}`}>Descripción</label>
                <textarea value={form.descripcion} onChange={e => setForm({...form, descripcion: e.target.value})}
                  rows={3} placeholder="Describe la tarea..."
                  className={`w-full rounded-xl px-4 py-2.5 text-sm focus:outline-none focus:ring-2 transition resize-none ${inputCls} ${formErrors.descripcion ? 'ring-2 ring-red-500' : ''}`}/>
                {formErrors.descripcion && <p className="text-red-400 text-xs mt-1">{formErrors.descripcion}</p>}
              </div>
              <div className="grid grid-cols-2 gap-4">
                {/* Asignado */}
                <div>
                  <label className={`block text-sm font-medium mb-1.5 ${subtext}`}>Asignado a</label>
                  <select value={form.asignado} onChange={e => setForm({...form, asignado: parseInt(e.target.value)})}
                    className={`w-full rounded-xl px-4 py-2.5 text-sm focus:outline-none focus:ring-2 transition ${inputCls} ${formErrors.asignado ? 'ring-2 ring-red-500' : ''}`}>
                    <option value="">Seleccionar...</option>
                    {USUARIOS.map(u => <option key={u.id} value={u.id}>{u.nombre}</option>)}
                  </select>
                  {formErrors.asignado && <p className="text-red-400 text-xs mt-1">{formErrors.asignado}</p>}
                </div>
                {/* Prioridad */}
                <div>
                  <label className={`block text-sm font-medium mb-1.5 ${subtext}`}>Prioridad</label>
                  <select value={form.prioridad} onChange={e => setForm({...form, prioridad: e.target.value})}
                    className={`w-full rounded-xl px-4 py-2.5 text-sm focus:outline-none focus:ring-2 transition ${inputCls}`}>
                    {PRIORIDADES.map(p => <option key={p} value={p}>{p}</option>)}
                  </select>
                </div>
              </div>
              {/* Estado */}
              <div>
                <label className={`block text-sm font-medium mb-1.5 ${subtext}`}>Estado</label>
                <div className="flex gap-2 flex-wrap">
                  {ESTADOS.map(e => (
                    <button key={e} type="button" onClick={() => setForm({...form, estado: e})}
                      className={`text-xs px-3 py-1.5 rounded-xl font-medium transition border
                        ${form.estado === e ? estadoStyle[e].pill + ' border-transparent' : `${darkMode ? 'border-white border-opacity-10 text-gray-400' : 'border-gray-200 text-gray-500'} hover:border-indigo-500`}`}>
                      {e}
                    </button>
                  ))}
                </div>
              </div>
            </div>
            <div className="flex gap-3 mt-6">
              <button onClick={() => setModalOpen(false)}
                className={`flex-1 py-2.5 rounded-xl text-sm font-medium border transition ${darkMode ? 'border-white border-opacity-10 text-gray-400 hover:bg-white hover:bg-opacity-5' : 'border-gray-200 text-gray-500 hover:bg-gray-50'}`}>
                Cancelar
              </button>
              <button onClick={saveForm}
                className="flex-1 bg-indigo-600 hover:bg-indigo-500 text-white py-2.5 rounded-xl text-sm font-medium transition">
                {editingTarea ? 'Guardar cambios' : 'Crear tarea'}
              </button>
            </div>
          </div>
        </div>
      )}

      {/* ── MODAL: Asignar ── */}
      {assignModal && (
        <div className="fixed inset-0 bg-black bg-opacity-70 flex items-center justify-center z-50 px-4">
          <div className={`w-full max-w-sm rounded-2xl shadow-2xl border p-6 ${darkMode ? 'bg-gray-800 border-white border-opacity-10' : 'bg-white border-gray-200'}`}>
            <h3 className={`text-lg font-bold mb-1 ${text}`}>Asignar Tarea</h3>
            <p className={`text-sm mb-4 ${subtext}`}>Selecciona el usuario al que deseas asignar esta tarea.</p>
            <div className="space-y-2 mb-4">
              {USUARIOS.map(u => (
                <button key={u.id} onClick={() => setAssignTarget(u.id)}
                  className={`w-full flex items-center gap-3 px-3 py-2.5 rounded-xl border transition
                    ${assignTarget === u.id
                      ? 'border-indigo-500 bg-indigo-600 bg-opacity-10'
                      : `${darkMode ? 'border-white border-opacity-10 hover:border-indigo-500' : 'border-gray-200 hover:border-indigo-400'}`}`}>
                  <div className={`w-8 h-8 ${u.color} rounded-full flex items-center justify-center text-white font-bold text-sm`}>{u.initials}</div>
                  <div className="text-left">
                    <p className={`text-sm font-medium ${text}`}>{u.nombre}</p>
                    <p className={`text-xs ${subtext}`}>@{u.usuario}</p>
                  </div>
                  {assignTarget === u.id && (
                    <svg className="w-4 h-4 text-indigo-400 ml-auto" fill="currentColor" viewBox="0 0 20 20"><path fillRule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clipRule="evenodd"/></svg>
                  )}
                </button>
              ))}
            </div>
            <div className="flex gap-3">
              <button onClick={() => setAssignModal(null)}
                className={`flex-1 py-2.5 rounded-xl text-sm font-medium border transition ${darkMode ? 'border-white border-opacity-10 text-gray-400 hover:bg-white hover:bg-opacity-5' : 'border-gray-200 text-gray-500 hover:bg-gray-50'}`}>
                Cancelar
              </button>
              <button onClick={saveAssign}
                className="flex-1 bg-indigo-600 hover:bg-indigo-500 text-white py-2.5 rounded-xl text-sm font-medium transition">
                Confirmar
              </button>
            </div>
          </div>
        </div>
      )}

      {/* ── MODAL: Crear / Editar Rol ── */}
      {rolModal && (
        <div className="fixed inset-0 bg-black bg-opacity-70 flex items-center justify-center z-50 px-4">
          <div className={`w-full max-w-lg rounded-2xl shadow-2xl border p-6 ${darkMode ? 'bg-gray-800 border-white border-opacity-10' : 'bg-white border-gray-200'} max-h-screen overflow-y-auto`}>
            <div className="flex items-center justify-between mb-5">
              <h3 className={`text-lg font-bold ${text}`}>{editingRol ? 'Editar Rol' : 'Nuevo Rol'}</h3>
              <button onClick={() => setRolModal(false)} className={`${subtext} hover:text-white transition`}>
                <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12"/></svg>
              </button>
            </div>
            <div className="space-y-4">
              <div>
                <label className={`block text-sm font-medium mb-1.5 ${subtext}`}>Nombre del Rol</label>
                <input value={rolForm.nombre} onChange={e => setRolForm({...rolForm, nombre: e.target.value})}
                  placeholder="Ej: Líder Técnico"
                  className={`w-full rounded-xl px-4 py-2.5 text-sm focus:outline-none focus:ring-2 transition ${inputCls} ${rolFormErrors.nombre ? 'ring-2 ring-red-500' : ''}`}/>
                {rolFormErrors.nombre && <p className="text-red-400 text-xs mt-1">{rolFormErrors.nombre}</p>}
              </div>
              <div>
                <label className={`block text-sm font-medium mb-1.5 ${subtext}`}>Descripción</label>
                <textarea value={rolForm.descripcion} onChange={e => setRolForm({...rolForm, descripcion: e.target.value})}
                  rows={2} placeholder="Describe las responsabilidades de este rol..."
                  className={`w-full rounded-xl px-4 py-2.5 text-sm focus:outline-none focus:ring-2 transition resize-none ${inputCls}`}/>
              </div>
              {/* Permisos agrupados */}
              <div>
                <label className={`block text-sm font-medium mb-3 ${subtext}`}>Permisos</label>
                {['Tareas','Equipo'].map(cat => (
                  <div key={cat} className="mb-4">
                    <p className={`text-xs font-semibold uppercase tracking-wide mb-2 ${subtext}`}>{cat}</p>
                    <div className="grid grid-cols-2 gap-2">
                      {PERMISOS_DISPONIBLES.filter(p => p.categoria === cat).map(p => {
                        const checked = rolForm.permisos.includes(p.key);
                        return (
                          <button key={p.key} type="button" onClick={() => togglePermiso(p.key)}
                            className={`flex items-center gap-2.5 px-3 py-2 rounded-xl text-sm text-left transition border
                              ${checked
                                ? 'border-indigo-500 bg-indigo-600 bg-opacity-15 text-indigo-300'
                                : `${darkMode ? 'border-white border-opacity-10 text-gray-400 hover:border-indigo-500' : 'border-gray-200 text-gray-500 hover:border-indigo-400'}`}`}>
                            <div className={`w-4 h-4 rounded flex items-center justify-center shrink-0 border transition
                              ${checked ? 'bg-indigo-600 border-indigo-600' : darkMode ? 'border-gray-600' : 'border-gray-300'}`}>
                              {checked && <svg className="w-3 h-3 text-white" fill="currentColor" viewBox="0 0 20 20"><path fillRule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clipRule="evenodd"/></svg>}
                            </div>
                            {p.label}
                          </button>
                        );
                      })}
                    </div>
                  </div>
                ))}
                <p className={`text-xs ${subtext} mt-1`}>{rolForm.permisos.length} permiso(s) seleccionado(s)</p>
              </div>
            </div>
            <div className="flex gap-3 mt-6">
              <button onClick={() => setRolModal(false)}
                className={`flex-1 py-2.5 rounded-xl text-sm font-medium border transition ${darkMode ? 'border-white border-opacity-10 text-gray-400 hover:bg-white hover:bg-opacity-5' : 'border-gray-200 text-gray-500 hover:bg-gray-50'}`}>
                Cancelar
              </button>
              <button onClick={saveRolForm}
                className="flex-1 bg-indigo-600 hover:bg-indigo-500 text-white py-2.5 rounded-xl text-sm font-medium transition">
                {editingRol ? 'Guardar cambios' : 'Crear Rol'}
              </button>
            </div>
          </div>
        </div>
      )}

      {/* ── MODAL: Asignar Rol a Usuario ── */}
      {assignRolModal && (
        <div className="fixed inset-0 bg-black bg-opacity-70 flex items-center justify-center z-50 px-4">
          <div className={`w-full max-w-sm rounded-2xl shadow-2xl border p-6 ${darkMode ? 'bg-gray-800 border-white border-opacity-10' : 'bg-white border-gray-200'}`}>
            <h3 className={`text-lg font-bold mb-1 ${text}`}>Cambiar Rol</h3>
            <p className={`text-sm mb-4 ${subtext}`}>
              Selecciona el nuevo rol para <span className="text-white font-medium">{USUARIOS.find(u => u.id === assignRolModal)?.nombre}</span>
            </p>
            <div className="space-y-2 mb-4">
              {roles.map(r => {
                const colorCls = rolColorStyle[r.nombre] || 'bg-indigo-500 bg-opacity-20 text-indigo-400 border-indigo-500 border-opacity-30';
                const selected = parseInt(assignRolTarget) === r.id;
                return (
                  <button key={r.id} onClick={() => setAssignRolTarget(r.id)}
                    className={`w-full flex items-center gap-3 px-3 py-2.5 rounded-xl border transition
                      ${selected ? 'border-indigo-500 bg-indigo-600 bg-opacity-10' : `${darkMode ? 'border-white border-opacity-10 hover:border-indigo-500' : 'border-gray-200 hover:border-indigo-400'}`}`}>
                    <span className={`text-xs px-2 py-1 rounded-lg font-bold border ${colorCls}`}>{r.nombre}</span>
                    <div className="text-left flex-1">
                      <p className={`text-xs ${subtext}`}>{r.permisos.length} permisos</p>
                    </div>
                    {selected && <svg className="w-4 h-4 text-indigo-400 ml-auto shrink-0" fill="currentColor" viewBox="0 0 20 20"><path fillRule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clipRule="evenodd"/></svg>}
                  </button>
                );
              })}
            </div>
            <div className="flex gap-3">
              <button onClick={() => { setAssignRolModal(null); setAssignRolTarget(''); }}
                className={`flex-1 py-2.5 rounded-xl text-sm font-medium border transition ${darkMode ? 'border-white border-opacity-10 text-gray-400 hover:bg-white hover:bg-opacity-5' : 'border-gray-200 text-gray-500 hover:bg-gray-50'}`}>
                Cancelar
              </button>
              <button onClick={saveAssignRol}
                className="flex-1 bg-indigo-600 hover:bg-indigo-500 text-white py-2.5 rounded-xl text-sm font-medium transition">
                Confirmar
              </button>
            </div>
          </div>
        </div>
      )}

      {/* ── MODAL: Confirmar eliminación Rol ── */}
      {deleteRolConfirm && (
        <div className="fixed inset-0 bg-black bg-opacity-70 flex items-center justify-center z-50 px-4">
          <div className={`w-full max-w-sm rounded-2xl shadow-2xl border p-6 ${darkMode ? 'bg-gray-800 border-white border-opacity-10' : 'bg-white border-gray-200'}`}>
            <div className="flex flex-col items-center text-center mb-5">
              <div className="w-12 h-12 bg-red-500 bg-opacity-20 rounded-full flex items-center justify-center mb-3">
                <svg className="w-6 h-6 text-red-400" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z"/></svg>
              </div>
              <h3 className={`text-lg font-bold ${text} mb-1`}>Eliminar Rol</h3>
              <p className={`text-sm ${subtext}`}>Los miembros con este rol quedarán sin rol asignado. ¿Confirmas?</p>
            </div>
            <div className="flex gap-3">
              <button onClick={() => setDeleteRolConfirm(null)}
                className={`flex-1 py-2.5 rounded-xl text-sm font-medium border transition ${darkMode ? 'border-white border-opacity-10 text-gray-400 hover:bg-white hover:bg-opacity-5' : 'border-gray-200 text-gray-500 hover:bg-gray-50'}`}>
                Cancelar
              </button>
              <button onClick={() => deleteRol(deleteRolConfirm)}
                className="flex-1 bg-red-600 hover:bg-red-500 text-white py-2.5 rounded-xl text-sm font-medium transition">
                Eliminar
              </button>
            </div>
          </div>
        </div>
      )}

      {/* ── MODAL: Confirmar eliminación ── */}
      {deleteConfirm && (
        <div className="fixed inset-0 bg-black bg-opacity-70 flex items-center justify-center z-50 px-4">
          <div className={`w-full max-w-sm rounded-2xl shadow-2xl border p-6 ${darkMode ? 'bg-gray-800 border-white border-opacity-10' : 'bg-white border-gray-200'}`}>
            <div className="flex flex-col items-center text-center mb-5">
              <div className="w-12 h-12 bg-red-500 bg-opacity-20 rounded-full flex items-center justify-center mb-3">
                <svg className="w-6 h-6 text-red-400" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z"/></svg>
              </div>
              <h3 className={`text-lg font-bold ${text} mb-1`}>Eliminar tarea</h3>
              <p className={`text-sm ${subtext}`}>Esta acción no se puede deshacer. ¿Confirmas la eliminación?</p>
            </div>
            <div className="flex gap-3">
              <button onClick={() => setDeleteConfirm(null)}
                className={`flex-1 py-2.5 rounded-xl text-sm font-medium border transition ${darkMode ? 'border-white border-opacity-10 text-gray-400 hover:bg-white hover:bg-opacity-5' : 'border-gray-200 text-gray-500 hover:bg-gray-50'}`}>
                Cancelar
              </button>
              <button onClick={() => deleteTarea(deleteConfirm)}
                className="flex-1 bg-red-600 hover:bg-red-500 text-white py-2.5 rounded-xl text-sm font-medium transition">
                Eliminar
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}