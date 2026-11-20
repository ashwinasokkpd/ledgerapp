import { Routes, Route } from "react-router-dom";
import ProtectedRoute from "../ProtectedRoute.jsx";
import AdminDashboard from "./AdminDashboard.jsx";
import FundTransferPage from "./FundTransferPage.jsx";
const AdminRoutes = () => {
  return (
    <Routes>
      <Route element={<ProtectedRoute allowedRoles={["ROLE_ADMIN"]} />}>
        <Route path="dashboard" element={<AdminDashboard />} />
        <Route path="fund-transfer" element={<FundTransferPage />} />
      </Route>
    </Routes>
  );
};
export default AdminRoutes;
