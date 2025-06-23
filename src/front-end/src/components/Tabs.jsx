import React from 'react';

const Tabs = ({ activeTab, onTabClick }) => {
    const tabs = [
        { id: 'discover', label: 'Grupos' },
        { id: 'joined', label: 'Unidos' },
        { id: 'created', label: 'Creados' },
    ];

    return (
        <div className="tabs-container">
            {tabs.map(tab => (
                <button
                    key={tab.id}
                    className={`tab-button ${activeTab === tab.id ? 'active' : ''}`}
                    onClick={() => onTabClick(tab.id)}
                >
                    {tab.label}
                </button>
            ))}
        </div>
    );
};

export default Tabs;