    const ip = import.meta.env.IP_PUBLICA;
    const puerto_back = import.meta.env.PUERTO_BACK;
    const puerto_admin = import .meta.env.PUERTO_FRONT_ADMIN;
    const puerto_user = import .meta.env.PUERTO_FRONT_USER;
    const protocolo = import.meta.env.PROTOCOLO;

    export const urlBaseBack =  ()=>{
        const rutaBase = `${protocolo}://${ip}:${puerto_back}`;
        console.log(rutaBase);
        return rutaBase;
    };

    export const urlBaseFrontAdmin = ()=>{
        return `${protocolo}://${ip}:${puerto_admin}`;
    }

    export const urlBaseFrontUser = ()=>{
        return `${protocolo}://${ip}:${puerto_user}`;
    }
