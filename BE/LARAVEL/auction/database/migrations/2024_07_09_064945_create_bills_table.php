<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateBillsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('bills', function (Blueprint $table) {
            // Bill_Id làm khóa chính, varchar(10)
            $table->string('Bill_Id', 10)->primary();

            // UserId, TaxId, AssetId làm khóa ngoại, varchar(10)
            $table->string('UserId', 10);
            $table->string('TaxId', 10);
            $table->string('AssetId', 10);

            // TotalAmount là double, Status là enum ['unpaid', 'paid'], PaymentTime là dateTime
            $table->double('TotalAmount');
            $table->enum('Status', ['unpaid', 'paid']);
            $table->dateTime('PaymentTime');

            // Định nghĩa các ràng buộc khóa ngoại
            $table->foreign('UserId')->references('UserId')->on('users');
            $table->foreign('TaxId')->references('TaxId')->on('taxes');
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
        Schema::dropIfExists('bills');
    }
}
