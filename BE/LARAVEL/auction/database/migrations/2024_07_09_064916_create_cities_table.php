<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateCitiesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('cities', function (Blueprint $table) {
            // CityId làm khóa chính, varchar(10)
            $table->string('CityId', 10)->primary();

            // Name và StateId là varchar(MAX), StateId làm khóa ngoại, varchar(10)
            $table->string('Name');
            $table->string('StateId', 10);

            // Định nghĩa các ràng buộc khóa ngoại
            $table->foreign('StateId')->references('StateId')->on('states');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('cities');
    }
}
