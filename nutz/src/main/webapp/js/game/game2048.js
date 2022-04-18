;(function($, window, document,undefined) {
    $.fn.game2048 = function(method) {
        this.each(function () {
            var game2048 = $(this).data('game2048');
            if (!game2048){
                if (!$(this).is('div')){
                    return;
                } else {
                    game2048 = new Game2048($(this));
                    return game2048.init();
                }
            }
            if (method){
                switch (method) {
                    case 'up':
                        return game2048.up();
                    case 'down':
                        return game2048.down();
                    case 'left':
                        return game2048.left();
                    case 'right':
                        return game2048.right();
                    case 'reset':
                        return game2048.reset();
                    case 'grade':
                        return game2048.grade;
                    default:
                        return;
                }
            } else {
                return game2048.init();
            }
        });
    };

    var Game2048 = function (ele) {
        this.$element = ele;
        this.default = {
            tdWidth : 100,
            tdHeight : 100
        };
        this.grade = 0;
        this.array;
        this.options;
    };

    Game2048.prototype = {
        init : function () {
            var $ele = this.$element;
            var obj = $ele.data();
            this.options = {
                tdWidth : obj.tdwidth || 100,
                tdHeight : obj.tdheight || 100
            };
            $ele.empty();
            var $table = $('<table style="border:1px solid rgba(13,0,0,0.23)" border="1px"></table>');
            var arr = getInitArr();
            this.array = arr;
            for (var x = 0; x < 4; x++){
                var $tr = $('<tr></tr>');
                for (var y = 0; y < 4; y++){
                    var $td = $('<td style="width: ' + this.options.tdWidth + 'px;height: ' + this.options.tdHeight + 'px;text-align: center;font-size: x-large;" border="1px"></td>');
                    setTdNumber($td, arr[x][y]);
                    $tr.append($td);
                }
                $table.append($tr);
            }
            $ele.append($table);
            $ele.data('game2048', this);
            return $ele;
        },
        reset : function () {
            var arr = getInitArr();
            var $table = this.$element.find('table');
            for (var x = 0; x < 4; x++){
                var $tr = $table.find('tr').eq(x);
                for (var y = 0; y < 4; y++){
                    setTdNumber($tr.find('td').eq(y), arr[x][y]);
                }
            }
            this.array = arr;
            this.grade = 0;
            return this.$element;
        },
        up : function () {
            var gameOver = true;
            for (var y = 0; y < 4; y++){
                var flag = {x : 3, y : y, val: this.array[3][y], added: false};
                for (var x = 2; x >= 0; x--){
                    this.grade += add(this.array, x, y, flag);
                    if (flag.added){
                        gameOver = false;
                    }
                }
                flag = undefined;
                for (var x = 0; x < 4; x++){
                    flag = compressZero(this.array, x,y, flag);
                }
                if (flag && flag.move){
                    gameOver = false;
                }
            }
            rebuiltTable(this.$element, this.array, gameOver);
            return this.$element;
        },
        down : function () {
            var gameOver = true;
            for (var y = 0; y < 4; y++){
                var flag = {x : 0, y : y, val: this.array[0][y], added: false};
                for (var x = 1; x < 4; x++){
                    this.grade += add(this.array, x, y, flag);
                    if (flag.added){
                        gameOver = false;
                    }
                }
                flag = undefined;
                for (var x = 3; x >=0; x--){
                    flag = compressZero(this.array, x,y, flag);
                }
                if (flag && flag.move){
                    gameOver = false;
                }
            }
            rebuiltTable(this.$element, this.array, gameOver);
            return this.$element;
        },
        left : function () {
            var gameOver = true;
            for (var x = 0; x < 4; x++){
                var flag = {x : x, y : 3, val: this.array[x][3], added: false};
                for (var y = 2; y >= 0; y--){
                    this.grade += add(this.array, x, y, flag);
                    if (flag.added){
                        gameOver = false;
                    }
                }
                flag = undefined;
                for (var y = 0; y < 4; y++){
                    flag = compressZero(this.array, x,y, flag);
                }
                if (flag && flag.move){
                    gameOver = false;
                }
            }
            rebuiltTable(this.$element, this.array, gameOver);
            return this.$element;
        },
        right : function () {
            var gameOver = true;
            for (var x = 0; x < 4; x++){
                var flag = {x : x, y : 0, val: this.array[x][0], added: false};
                for (var y = 1; y < 4; y++){
                    this.grade += add(this.array, x, y, flag);
                    if (flag.added){
                        gameOver = false;
                    }
                }
                flag = undefined;
                for (var y = 3; y >= 0; y--){
                    flag = compressZero(this.array, x,y, flag);
                }
                if (flag && flag.move){
                    gameOver = false;
                }
            }
            rebuiltTable(this.$element, this.array, gameOver);
            return this.$element;
        }
    };

    /**
     * 获取初始化的二维数组
     * @returns {[*,*,*,*]}
     */
    function getInitArr() {
        var x1 = Math.floor(Math.random() * 4);
        var y1 = Math.floor(Math.random() * 4);
        var x2 = Math.floor(Math.random() * 4);
        var y2;
        if (x1 == x2){
            while (true){
                y2 = Math.floor(Math.random() * 4);
                if (y2 != y1){
                    break;
                }
            }
        } else {
            y2 = Math.floor(Math.random() * 4);
        }
        var arr = [[0,0,0,0,],[0,0,0,0,],[0,0,0,0,],[0,0,0,0,]];
        arr[x1][y1] = 2;
        arr[x2][y2] = 2;
        return arr;
    }

    /**
     * 判断两个位置数字是否要相加
     * @param arr
     * @param x
     * @param y
     * @param flag
     * @returns {*}
     */
    function add(arr, x, y, flag) {
        var val = arr[x][y];
        if (val != 0){
            if (val == flag.val && !flag.added){
                arr[flag.x][flag.y] = 0;
                val = val*2;
                arr[x][y] = val;
                flag.x = x;
                flag.y = y;
                flag.val = val;
                flag.added = true;
                return val;
            } else {
                flag.x = x;
                flag.y = y;
                flag.val = val;
                flag.added = false;
                return 0;
            }
        }
        return 0;
    }

    /**
     * 去0
     * @param arr
     * @param x
     * @param y
     * @param flag
     * @returns {*}
     */
    function compressZero(arr, x, y, flag) {
        var val = arr[x][y];
        if (val == 0 && !flag){
            flag = {x : x, y : y, move:false};
        } else if (val != 0 && flag){
            arr[flag.x][flag.y] = val;
            arr[x][y] = 0;
            flag = {x : x, y : y, move:true};
        }
        return flag;
    }

    /**
     * 根据数组重构table
     * @param gameOver 操作行、列是否不可操作
     */
    function rebuiltTable($table, arr, gameOver) {
        var zeros = [];
        for (var x = 0; x < 4; x++){
            for (var y = 0; y < 4; y++){
                var val =  arr[x][y];
                if (val == 0){
                    zeros.push({x : x, y : y});
                }
                setTdNumber($table.find('tr').eq(x).find('td').eq(y),arr[x][y]);
            }
        }
        if (gameOver){
            if (isGameOver(arr)){
                alert("GAMEOVER");
                return;
            }
        } else {
            var len = zeros.length;
            if (len > 0) {
                var i = Math.floor(Math.random() * len);
                var zero = zeros[i];
                arr[zero.x][zero.y] = 2;
                setTdNumber($table.find('tr').eq(zero.x).find('td').eq(zero.y), 2);
            }
        }
    }

    /**
     * 判断游戏是否结束
     * @returns {boolean}
     */
    function isGameOver(arr) {
        for (var x = 0; x < 4; x++){
            for (var y = 0; y < 4; y++){
                var val =  arr[x][y];
                if (val == 0){
                    return false;
                }
                if (x != 3 && val == arr[x+1][y]){
                    return false;
                }
                if (y != 3 && val == arr[x][y+1]){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 设置td数字和颜色
     * @param $td
     * @param num
     */
    function setTdNumber($td, num) {
        switch (num) {
            case 0:
                $td.css('background-color', '#e5e5e5');
                break;
            case 2:
                $td.css('background-color', '#FFF5EE');
                break;
            case 4:
                $td.css('background-color', '#FAF0E6');
                break;
            case 8:
                $td.css('background-color', '#FFDAB9');
                break;
            case 16:
                $td.css('background-color', '#FFA07A');
                break;
            case 32:
                $td.css('background-color', '#FF6347');
                break;
        }
        $td.html(num);
    }

    $(function () {
        $('div.game2048').game2048();
    });

})(jQuery, window, document);