<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateAuctionsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('auctions', function (Blueprint $table) {
            // AuctionId làm khóa chính, varchar(10)
            $table->string('AuctionId', 10)->primary();

            // Name, Method, IsSecret là varchar(MAX) và enum
            $table->string('Name');
            $table->enum('Method', ['on', 'off']);
            $table->enum('IsSecret', ['secret', 'unsecret']);

            // Status là enum ['upcoming','ongoing','finished'], StartDate và EndDate là DateTime
            $table->enum('Status', ['upcoming', 'ongoing', 'finished']);
            $table->dateTime('StartDate');
            $table->dateTime('EndDate');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('auctions');
    }
}
