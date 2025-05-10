import React from "react";

const Modal = ({ title, message, onCancel, onConfirm }) => {
    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <h3>{title}</h3>
                <p>{message}</p>
                <div className="modal-actions">
                    <button className="cancel-button" onClick={onCancel}>Cancel</button>
                    <button className="confirm-button" onClick={onConfirm}>Confirm</button>
                </div>
            </div>
        </div>
    );
};

export default Modal;
