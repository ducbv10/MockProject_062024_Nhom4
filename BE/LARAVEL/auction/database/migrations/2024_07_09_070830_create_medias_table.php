<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateMediasTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('medias', function (Blueprint $table) {
            // MediaId làm khóa chính, varchar(10)
            $table->string('MediaId', 10)->primary();

            // AssetId làm khóa ngoại, varchar(10)
            $table->string('AssetId', 10);

            // Name là varchar(MAX), ZipCodeId làm khóa ngoại, varchar(10)
            $table->string('Name');
            $table->string('ZipCodeId', 10);

            // Định nghĩa các ràng buộc khóa ngoại
            $table->foreign('AssetId')->references('AssetId')->on('assets');
            $table->foreign('ZipCodeId')->references('ZipCodeId')->on('zip_codes');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('medias');
    }
}
