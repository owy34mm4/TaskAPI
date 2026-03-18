import InputField from './InputField';
import PasswordField from './PasswordField';

export default function LoginForm(props) {
  return (
    <form onSubmit={(e)=>{e.preventDefault(); props.onSubmit();}}>

      <InputField
        label="Correo"
        value={props.email}
        onChange={(e)=>props.setEmail(e.target.value)}
      />

      <PasswordField
        value={props.password}
        onChange={(e)=>props.setPassword(e.target.value)}
        show={props.showPassword}
        toggle={()=>props.setShowPassword(!props.showPassword)}
      />

      {props.error && <p className="text-red-400">{props.error}</p>}

      <button className="bg-indigo-600 w-full mt-3 p-2 text-white">
        {props.loading ? '...' : 'Login'}
      </button>
    </form>
  );
}