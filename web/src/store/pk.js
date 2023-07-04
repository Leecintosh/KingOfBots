export default {
    state: {
        status: "matching", // matching for matching page, playing for battle page
        socket: null,
        opponent_username: "",
        opponent_photo: "",
        game_map: null,
        a_id: 0,
        a_sx: 0,
        a_sy: 0,
        b_id: 0,
        b_sx: 0,
        b_sy: 0,
        game_object: null,
        loser: "none", // none, all, A, B
    },
    getters: {
    },
    mutations: {
        updateSocket(state, socket) {
            state.socket = socket;
        },
        updateOpponent(state, opponent) {
            state.opponent_username = opponent.username;
            state.opponent_photo = opponent.photo;
        },
        updateStatus(state, status) {
            state.status = status;
        },
        updateGame(state, game) {
            state.game_map = game.map;
            state.a_id = game.a_id;
            state.a_sx = game.a_sx;
            state.a_sy = game.a_sy;
            state.b_id = game.b_id;
            state.b_sx = game.b_sx;
            state.b_sy = game.b_sy;
        },
        updateGameObject(state, game_object) {
            state.game_object = game_object;
        },
        updateLoser(state, loser) {
            state.loser = loser;
        }
    },
    actions: {
    },
    modules: {
    }
}
