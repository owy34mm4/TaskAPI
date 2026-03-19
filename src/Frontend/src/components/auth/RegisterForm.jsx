import InputField from './InputField';
import PasswordStrength from './PasswordStrength';

export default function RegisterForm(props) {
  return (
    <form onSubmit={(e)=>{e.preventDefault(); props.onSubmit();}}>

      <InputField 
        label="Nombre" 
        value={props.form?.nombre || ''} 
        onChange={props.handleChange('nombre')} 
      />
      
      <InputField 
        label="Correo" 
        value={props.form?.correo || ''} 
        onChange={props.handleChange('correo')} 
      />
      
      <InputField 
        label="Usuario" 
        value={props.form?.usuario || ''} 
        onChange={props.handleChange('usuario')} 
      />

      <InputField 
        type="password" 
        label="Password" 
        value={props.form?.password || ''} 
        onChange={props.handleChange('password')} 
      />

      <PasswordStrength password={props.form?.password || ''} />

      {/* 👇 MOSTRAR ERROR */}
      {props.errors?.general && (
        <p className="text-red-500 mt-2">
          {props.errors.general}
        </p>
      )}

      <button className="bg-indigo-600 w-full mt-3 p-2 text-white">
        {props.loading ? '...' : 'Register'}
      </button>
    </form>
  );
}