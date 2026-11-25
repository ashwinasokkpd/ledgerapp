import { Routes, Route } from "react-router-dom";
import ProtectedRoute from "../ProtectedRoute.jsx";
import AdminDashboard from "./AdminDashboard.jsx";
import FundTransferPage from "./FundTransferPage.jsx";
import Deposit from "./Deposit.jsx"
import Withdraw from "./Withdraw.jsx";
const AdminRoutes = () => {
  return (
    <Routes>
      <Route element={<ProtectedRoute allowedRoles={["ROLE_ADMIN"]} />}>
        <Route path="dashboard" element={<AdminDashboard />} />
        <Route path="fund-transfer" element={<FundTransferPage />} />
        <Route path="deposit" element={<Deposit />} />
        <Route path="withdraw" element={<Withdraw />} />
      </Route>
    </Routes>
  );
};
export default AdminRoutes;
