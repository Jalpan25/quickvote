import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'; 
import LoginPage from '../pages/LoginPage';
import RegistrationForm from './RegistrationForm';
import Dashboard from '../pages/Dashboard';
import HomePage1 from '../pages/HomePage1.jsx';
import '@fortawesome/fontawesome-free/css/all.min.css';
import LoginParticipant from '../pages/LoginParticipant';
import QuestionPage from '../pages/QuestionPage';
import AdminDashboard from '../pages/AdminDashboard';
import SurveyResult from '../pages/SurveyResult';
import EditSurvey from '../pages/EditSurvey.jsx'
import CreateSurvey from '../pages/CreateSurvey.jsx';
import CreatePoll from '../pages/CreatePoll.jsx';
import SuperAdminDashboard from '../pages/SuperAdminDashboard.jsx';
import AdminResult from '../pages/AdminResultPage.jsx';
import SimplePage from '../pages/SimplePage.jsx'
import Features from './Features.jsx';
function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<HomePage1 />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegistrationForm />} />
        <Route path="/loginparticipant" element={<LoginParticipant />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/questionpage" element={<QuestionPage />} />
        <Route path="/admindashboard" element={<AdminDashboard />} />
        <Route path="/CreateSurvey" element={<CreateSurvey />} />
        <Route path="/CreatePoll" element={<CreatePoll />} />
        <Route path="/surveyresult" element={<SurveyResult />} /> 
        <Route path="/editsurvey" element={<EditSurvey />} /> 
        <Route path="/superadmindashboard" element={<SuperAdminDashboard />} />
        <Route path="/adminresult" element={<AdminResult />} /> 
        <Route path="/simplepage" element={<SimplePage />} />  
        <Route path="/feautres" element={<Features />} />
      </Routes>
    </Router>
  );
}

export default App;
