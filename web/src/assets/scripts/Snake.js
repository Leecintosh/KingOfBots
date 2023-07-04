import { AcGameObject } from "./AcGameObject";
import { Cell } from "./Cell";

export class Snake extends AcGameObject {
    constructor(info, gamemap) {
        super();

        this.id = info.id;
        this.color = info.color;
        this.gamemap = gamemap;

        this.cells = [new Cell(info.r, info.c)]; // store the cells of the snake, cells[0] is the head
        this.next_cell = null; // the next cell to move to

        this.speed = 5; // cells per second
        this.direction = -1; // -1: stop, 0: up, 1: right, 2: down, 3: left
        this.status = "idle"; // idle, move, die

        this.dr = [-1, 0, 1, 0]; // row offset for each direction 
        this.dc = [0, 1, 0, -1]; // col offset for each direction

        this.step = 0; // current step
        this.eps = 1e-2; // epsilon for comparing float numbers

        this.eye_direction = 0; // 0: right, 1: left, 2: up, 3: down
        if (this.id === 1) this.eye_direction = 2; // left bottom snake looks up, right top snake looks down

        this.eye_dx = [ // eye x offset for each direction
            [-1, 1], // right
            [1, 1], // left
            [1, -1], // up
            [-1, -1] // down
        ];

        this.eye_dy = [ // eye y offset for each direction
            [-1, -1], // right
            [-1, 1], // left
            [1, 1], // up
            [-1, 1] // down
        ];
    }

    start() {
    }

    set_direction(d) {
        this.direction = d;
    }

    check_tail_increasing() { // check if the tail is increasing at current step
        if (this.step <= 10) return true;
        if (this.step % 3 === 1) return true;
        return false;
    }

    next_step() { // go to the next step
        const d = this.direction;
        this.next_cell = new Cell(this.cells[0].r + this.dr[d], this.cells[0].c + this.dc[d]);
        this.eye_direction = d;
        this.direction = -1; // reset direction
        this.status = "move";
        this.step ++;

        const k = this.cells.length;
        for (let i = k; i > 0; i--) {
            this.cells[i] = JSON.parse(JSON.stringify(this.cells[i - 1]));
        }
    }

    update_move() {
        const dx = this.next_cell.x - this.cells[0].x;
        const dy = this.next_cell.y - this.cells[0].y;
        const distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < this.eps) { // reach the target cell
            this.cells[0] = this.next_cell; // add a new cell as the head
            this.next_cell = null;
            this.status = "idle"; // stop moving

            if (!this.check_tail_increasing()) { // if the tail is not increasing, remove the tail
                this.cells.pop(); // remove the tail
            }
        } else {
            const move_distance = this.speed * this.timedelta / 1000; // distance between 2 frames
            this.cells[0].x += dx / distance * move_distance;
            this.cells[0].y += dy / distance * move_distance;

            if (!this.check_tail_increasing()) { // add a new cell as the tail
                const k = this.cells.length;
                const tail = this.cells[k - 1], tail_target = this.cells[k - 2];
                const tail_dx = tail_target.x - tail.x;
                const tail_dy = tail_target.y - tail.y;
                tail.x += tail_dx / distance * move_distance;
                tail.y += tail_dy / distance * move_distance;

            }
        }
    }

    update() { // every frame update, 60 fps
        if (this.status === "move") {
            this.update_move();
        }
        this.render();
    }

    render() {
        const L = this.gamemap.L;
        const ctx = this.gamemap.ctx;

        ctx.fillStyle = this.color;
        if (this.status === "die") {
            ctx.fillStyle = "white";
        }

        for (const cell of this.cells) {
            ctx.beginPath();
            ctx.arc(cell.x * L, cell.y * L, L / 2 * 0.8, 0, 2 * Math.PI);
            ctx.fill();
        }

        for (let i = 1; i < this.cells.length; i++) {
            const a = this.cells[i - 1], b = this.cells[i];
            if (Math.abs(a.x - b.x) < this.eps && Math.abs(a.y - b.y) < this.eps) continue;
            if (Math.abs(a.x - b.x) < this.eps) {
                ctx.fillRect((a.x - 0.4) * L, Math.min(a.y, b.y) * L, L * 0.8, Math.abs(a.y - b.y) * L);
            } else {
                ctx.fillRect(Math.min(a.x, b.x) * L, (a.y - 0.4) * L, Math.abs(a.x - b.x) * L, L * 0.8);
            }
        }

        ctx.fillStyle = "black";
        for (let i = 0; i < 2; i++) {
            const eye_x = (this.cells[0].x + this.eye_dx[this.eye_direction][i] * 0.15) * L;
            const eye_y = (this.cells[0].y + this.eye_dy[this.eye_direction][i] * 0.15) * L;

            ctx.beginPath();
            ctx.arc(eye_x, eye_y, L * 0.05, 0, Math.PI * 2);
            ctx.fill();
        }
    }
}