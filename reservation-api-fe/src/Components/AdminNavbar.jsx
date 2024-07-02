import DropDowns from "./DropDowns";
import '../Styles/AdminNavbar.css'

const AdminNavbar = () => {
    return (  
        <div className="adminnavbar">
            <div className="logo">
                <h1><img src="https://cdn.icon-icons.com/icons2/1047/PNG/256/bus_icon-icons.com_76529.png" alt="" /><i>yatra</i><sup><i>.in</i></sup></h1>
            </div>

            <div className="options">
                <DropDowns/>
            </div>
        </div>
    );
}
 
export default AdminNavbar;