import { useState } from "react";
import '../Styles/AddBus.css'
import axios from "axios";
import { type } from '@testing-library/user-event/dist/type'

const AddBus = () => {

    let [name, setname] = useState('')
    let [dod, setdod] = useState('')
    let [bus_number, setbus_number] = useState('')
    let [no_of_seats, setno_of_seats] = useState('')
    let [from_loc, setfrom_loc] = useState('')
    let [to_loc, setto_loc] = useState('')

    let busData = {
        name, dod, bus_number, no_of_seats, from_loc, to_loc
    }

    let admin = JSON.parse(localStorage.getItem("Admin"))

    function addBusData(e){
        e.preventDefault()

        axios.post(`http://localhost:8080/api/buses/${admin.id}`, busData)
        .then( (res)=>{
            alert("Bus Details has been added successfully")
            console.log(res);
        })
        .catch( (err)=>{
            alert("Invalid Data Format")
            console.log(err);
        })
    }

    return ( 
        <div className="addbus">
            <form action="" onSubmit={addBusData}>
                <h2>Add Bus</h2>

                <label htmlFor="">Name : </label>
                <input type="text" value={name} onChange={ (e)=>{setname(e.target.value)}} placeholder='Enter the name' required />

                <label htmlFor="">Bus number : </label>
                <input type="text" value={bus_number} onChange={ (e)=>{setbus_number(e.target.value)}} placeholder='Enter the bus number' required />

                <label htmlFor="">Date of departure : </label>
                <input type="date" value={dod} onChange={ (e)=>{setdod(e.target.value)}} placeholder='Enter the date of departure' required />

                <label htmlFor="">From Location : </label>
                <input type="text" value={from_loc} onChange={ (e)=>{setfrom_loc(e.target.value)}} placeholder='From location' required />

                <label htmlFor="">To Location : </label>
                <input type="text" value={to_loc} onChange={ (e)=>{setto_loc(e.target.value)}} placeholder='To location' required />

                <label htmlFor="">No of seats : </label>
                <input type="number" value={no_of_seats} onChange={ (e)=>{ setno_of_seats(e.target.value)}} placeholder='No of seats' required />

                <button className="btn btn-primary" type="submit">Add</button>

            </form>
        </div>
     );
}
export default AddBus;