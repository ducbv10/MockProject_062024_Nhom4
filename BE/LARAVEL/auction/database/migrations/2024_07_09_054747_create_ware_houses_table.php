<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateWareHousesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('ware_houses', function (Blueprint $table) {
            // WareHouseId làm khóa chính, varchar(10)
            $table->string('WareHouseId', 10)->primary();

            // Position là varchar(MAX), Quantity là double
            $table->string('Position');
            $table->double('Quantity');

            // importDate và exportDate là DateTime
            $table->dateTime('importDate');
            $table->dateTime('exportDate');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('ware_houses');
    }
}
