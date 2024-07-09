<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateTaxesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('taxes', function (Blueprint $table) {
            // TaxId làm khóa chính, varchar(10)
            $table->string('TaxId', 10)->primary();

            // UserId làm khóa ngoại, varchar(10)
            $table->string('UserId', 10);

            // TypeTax và Location là varchar(MAX), Value là double
            $table->string('TypeTax');
            $table->string('Location');
            $table->double('Value');

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
        Schema::dropIfExists('taxes');
    }
}
