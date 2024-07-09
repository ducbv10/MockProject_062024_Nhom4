<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateAddressesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('addresses', function (Blueprint $table) {
            // AddressId làm khóa chính, varchar(10)
            $table->string('AddressId', 10)->primary();

            // Name là varchar(MAX), ZipCodeId và UserId làm khóa ngoại, varchar(10)
            $table->string('Name');
            $table->string('ZipCodeId', 10);
            $table->string('UserId', 10);

            // Định nghĩa các ràng buộc khóa ngoại
            $table->foreign('ZipCodeId')->references('ZipCodeId')->on('zip_codes');
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
        Schema::dropIfExists('addresses');
    }
}
