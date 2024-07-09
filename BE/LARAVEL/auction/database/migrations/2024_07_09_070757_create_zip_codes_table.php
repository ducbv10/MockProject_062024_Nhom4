<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateZipCodesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('zip_codes', function (Blueprint $table) {
            // ZipCodeId làm khóa chính, varchar(10)
            $table->string('ZipCodeId', 10)->primary();

            // Name là varchar(MAX), CityId làm khóa ngoại, varchar(10)
            $table->string('Name');
            $table->string('CityId', 10);

            // Định nghĩa các ràng buộc khóa ngoại
            $table->foreign('CityId')->references('CityId')->on('cities');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('zip_codes');
    }
}
