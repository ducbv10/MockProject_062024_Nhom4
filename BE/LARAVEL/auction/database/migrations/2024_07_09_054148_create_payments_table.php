<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreatePaymentsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('payments', function (Blueprint $table) {
            // PaymentId làm khóa chính, varchar(10)
            $table->string('PaymentId', 10)->primary();

            // UserId làm khóa ngoại, varchar(10)
            $table->string('UserId', 10);

            // BankName, BankNum, BankBranch là varchar(MAX)
            $table->string('BankName');
            $table->string('BankNum');
            $table->string('BankBranch');

            // AccountBalance là double
            $table->double('AccountBalance');

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
        Schema::dropIfExists('payments');
    }
}
