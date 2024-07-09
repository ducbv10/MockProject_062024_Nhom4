<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateTransactionsHistoryTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('transactions_history', function (Blueprint $table) {
            // TransactionHistoryId làm khóa chính, varchar(10)
            $table->string('TransactionHistoryId', 10)->primary();

            // UserId và AuctionDetailId làm khóa ngoại, varchar(10)
            $table->string('UserId', 10);
            $table->string('AuctionDetailId', 10);

            // TradeDate là datetime, TradeCost và DepositCost là double
            $table->dateTime('TradeDate');
            $table->double('TradeCost');
            $table->double('DepositCost');

            // Note là varchar(max), Status là enum['successful', 'failed']
            $table->text('Note')->nullable();
            $table->enum('Status', ['successful', 'failed']);

            // Định nghĩa các ràng buộc khóa ngoại
            $table->foreign('UserId')->references('UserId')->on('users');
            $table->foreign('AuctionDetailId')->references('AuctionDetailId')->on('auction_details');

        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('transactions_history');
    }
}
