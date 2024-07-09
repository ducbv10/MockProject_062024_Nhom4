<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateNotificationsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('notifications', function (Blueprint $table) {
            // NotificationId làm khóa chính, varchar(10)
            $table->string('NotificationId', 10)->primary();

            // UserId làm khóa ngoại, varchar(10)
            $table->string('UserId', 10);

            // Content và Title là varchar(MAX)
            $table->string('Content');
            $table->string('Title');

            // Định nghĩa các ràng buộc khóa ngoại
            $table->foreign('UserId')->references('UserId')->on('users');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('notifications');
    }
}
