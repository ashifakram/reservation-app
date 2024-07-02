import { useState } from 'react';
import '../Styles/AdminSignUp.css'
import axios from 'axios';

const AdminSignUp = () => {

    let [name, setname] = useState("")
    let [email, setemail] = useState("")
    let [phone, setphone] = useState("")
    let [gst_number, setgst] = useState("")
    let [travels_name, settravels] = useState("")
    let [password, setpassword] = useState("")

    let data = {
        name, email, phone, gst_number, travels_name, password
    }

    function createAdmin(e) {
        
        e.preventDefault()
        axios.post('http://localhost:8080/api/admins', data)
        .then( (res)=>{
            alert('Admin Added Successfully')
            console.log(res)
        })
        .catch( (err)=>{
            alert('Invalid Data')
            console.log(err);
        })
    }


    return ( 
        <div className="adminsignup">
            <form onSubmit={createAdmin} action="">

                <h2>Admin SignUp</h2>

                <label htmlFor="">Name : </label>
                <input type="text" value={name} onChange={ (e)=>{setname(e.target.value)}} placeholder='Enter the name' required />

                <label htmlFor="">Email : </label>
                <input type="email" value={email} onChange={ (e)=>{setemail(e.target.value)}} placeholder='Enter the email' required />

                <label htmlFor="">Phone : </label>
                <input type="tel" value={phone} onChange={ (e)=>{setphone(e.target.value)}} placeholder='Enter the Phone number' required />

                <label htmlFor="">Gst number : </label>
                <input type="text" value={gst_number} onChange={ (e)=>{setgst(e.target.value)}} placeholder='Enter the Gst number' required />

                <label htmlFor="">Travels name : </label>
                <input type="text" value={travels_name} onChange={ (e)=>{settravels(e.target.value)}} placeholder='Enter the Travels name' required />

                <label htmlFor="">Password  : </label>
                <input type="password" value={password} onChange={ (e)=>{ setpassword(e.target.value)}} placeholder='Enter the password' required />

                <button className="btn btn-primary" type="submit">Register</button>
            </form>
        </div>
     );
}
 
export default AdminSignUp;
