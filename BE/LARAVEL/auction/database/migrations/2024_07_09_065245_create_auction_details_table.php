<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateAuctionDetailsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('auction_details', function (Blueprint $table) {
            // AuctionDetailId làm khóa chính, varchar(10)
            $table->string('AuctionDetailId', 10)->primary();

            // StartingPrice, PresentPrice, PercentPrice, Step là double
            $table->double('StartingPrice');
            $table->double('PresentPrice');
            $table->double('PercentPrice');
            $table->double('Step');

            // WonId, AuctionId, HostId, AssetId làm khóa ngoại, varchar(10)
            $table->string('WonId', 10);

            // TotalTime là varchar(10)
            $table->string('TotalTime', 10);

            $table->string('AuctionId', 10);
            $table->string('HostId', 10);
            $table->string('AssetId', 10);

            // Định nghĩa các ràng buộc khóa ngoại
            $table->foreign('WonId')->references('UserId')->on('users');
            $table->foreign('AuctionId')->references('AuctionId')->on('auctions');
            $table->foreign('HostId')->references('UserId')->on('users');
            $table->foreign('AssetId')->references('AssetId')->on('assets');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('auction_details');
    }
}
