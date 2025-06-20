
let mockGroups = [
    { id: 1, name: 'Manejo de la Ansiedad' }, { id: 2, name: 'Viviendo con TDAH' }, { id: 3, name: 'Superando la Depresión' }, { id: 4, name: 'Crecimiento Personal' }
];
let mockPosts = [
    { id: 101, groupId: 1, user: 'Ana Pérez', question: '¿Técnicas de respiración para ataques de pánico?', content: 'He probado la respiración 4-7-8 pero me gustaría conocer otras...', votes: 12 }, { id: 102, groupId: 1, user: 'Carlos Ruiz', question: 'Recomendaciones de apps de meditación', content: 'Busco una app con meditaciones guiadas para la ansiedad...', votes: 8 }, { id: 201, groupId: 2, user: 'Sofía Lara', question: 'Estrategias para mantener el foco', content: 'Trabajo desde casa y me cuesta mucho mantenerme enfocada...', votes: 25 },
];

const simulateNetwork = (delay) => new Promise(res => setTimeout(res, delay));

export const fetchGroups = async (searchTerm = '') => {
    await simulateNetwork(300);
    if (!searchTerm) return [...mockGroups];
    const lowerCaseSearchTerm = searchTerm.toLowerCase();
    return mockGroups.filter(group => group.name.toLowerCase().includes(lowerCaseSearchTerm));
};

export const fetchPostsByGroup = async (groupId) => {
    await simulateNetwork(500);
    return mockPosts.filter(p => p.groupId === groupId).sort((a,b) => b.id - a.id);
};

export const submitNewPost = async ({ groupId, title, content }) => {
    await simulateNetwork(700);
    const newPost = { id: Date.now(), groupId, user: "Tú", question: title, content, votes: 0 };
    mockPosts.push(newPost);
    return newPost;
};

export const submitNewGroup = async ({ name, description }) => {
    await new Promise(res => setTimeout(res, 800));
    const newGroup = {
        id: Date.now(),
        name,
        icon: 'NEW',
    };
    mockGroups.push(newGroup);
    return newGroup;
};