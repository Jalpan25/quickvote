
import React from 'react';
import { Link } from 'react-router-dom';
import voteImage from '../Assets/ps2.png';

const HomePage = () => {
    return (
        <div className="min-h-screen flex flex-col items-center justify-center bg-gradient-to-r from-blue-50 to-purple-50 p-6">
            {/* Background Animation */}
            <div className="absolute inset-0 overflow-hidden pointer-events-none">
                <div className="absolute w-[500px] h-[500px] bg-blue-400 opacity-20 blur-[150px] animate-pulse rounded-full top-[-150px] left-[-100px]"></div>
                <div className="absolute w-[400px] h-[400px] bg-purple-400 opacity-30 blur-[150px] animate-pulse rounded-full bottom-[-150px] right-[-100px]"></div>
            </div>

            {/* Main Content */}
            <div className="min-h-screen flex flex-col md:flex-row items-center justify-center w-full relative z-10">
                {/* Left Section - Image */}
                <div className="md:w-1/2 flex justify-center items-center mb-8 md:mb-0">
                    <img
                        src={voteImage}
                        alt="Vote Icon"
                        className="w-80 md:w-96 lg:w-[400px] transform transition-transform duration-500 hover:scale-110 drop-shadow-xl"
                    />
                </div>

                {/* Right Section - Content */}
                <div className="md:w-1/2 text-center md:text-left px-6">
                    <h3 className="text-xl md:text-2xl font-medium text-gray-700 mb-4 tracking-wide">
                        Be a part of the decision
                    </h3>
                    <h1 className="text-4xl md:text-5xl lg:text-6xl font-extrabold text-blue-700 mb-8 leading-tight">
                        Vote Today, Shape Tomorrow
                    </h1>

                    <p className="text-lg text-gray-600 mb-8">
                        Make your voice count with our secure and easy-to-use voting platform.
                    </p>

                    {/* Buttons */}
                    <div className="flex flex-col md:flex-row gap-6 justify-center md:justify-start">
                        <Link
                            to="/loginparticipant"
                            className="flex items-center justify-center gap-3 px-8 py-3 rounded-xl font-semibold text-white bg-blue-600 hover:bg-blue-700 transition-all duration-300 transform hover:scale-105 shadow-lg"
                        >
                            <i className="fas fa-user-circle text-xl"></i>
                            <span>Participant</span>
                        </Link>
                        <Link
                            to="/login"
                            className="flex items-center justify-center gap-3 px-8 py-3 rounded-xl font-semibold text-white bg-purple-600 hover:bg-purple-700 transition-all duration-300 transform hover:scale-105 shadow-lg"
                        >
                            <i className="fas fa-shield-alt text-xl"></i>
                            <span>Admin</span>
                        </Link>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default HomePage;
