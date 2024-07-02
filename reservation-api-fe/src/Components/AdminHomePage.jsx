import AddBus from "./AddBus";
import AdminDashBoard from "./AdminDashBoard";
import AdminNavbar from "./AdminNavbar";
import { Route, Routes } from "react-router-dom";

const AdminHomePage = () => {
    return ( 
        <div className="adminhomepage">
            <AdminNavbar/>
            <Routes>
                <Route path='/' element={<AdminDashBoard/>} />
                <Route path='/addbus' element={<AddBus/>} />
            </Routes>
        </div>
     );
}
 
export default AdminHomePage;
