import React, { useState } from 'react';
import '../Styles/AdminLogin.css'
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';

const AdminLogin = () => {

    let [email, setemail] = useState("")
    let [password, setpassword] = useState("")
    let navigate = useNavigate()


    function verify(e){
        e.preventDefault()
        axios.post(`http://localhost:8080/api/admins/verify-by-email-and-password?email=${email}&password=${password}`)
        .then( (res)=>{

            navigate('/adminhomepage')
            alert("Login Successful")
        })
        .catch( (err)=>{
            alert("Invalid Email id or password")
        })
        
    }

    return ( 
        <div className="adminlogin">
            <form onSubmit={verify} action="">
                <h2>Admin SignIn </h2>

                <label htmlFor="">Email : </label>
                <input type="email" value={email} onChange={(e)=>{setemail(e.target.value)}} placeholder='Enter the email' required />

                <label htmlFor="">Password : </label>
                <input type="password" value={password} onChange={(e)=>{setpassword(e.target.value)}} placeholder='Enter the password' required/>
                <button className='btn btn-primary'>Login</button>
                
                <p>New Admin ? <Link to='/adminsignup' >Register Here</Link></p>
            </form>
        </div>
     );
}
 
export default AdminLogin;