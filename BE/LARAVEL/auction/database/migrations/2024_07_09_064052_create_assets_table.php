<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateAssetsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('assets', function (Blueprint $table) {
            // AssetId làm khóa chính, varchar(10)
            $table->string('AssetId', 10)->primary();

            // Name, Describle, Origin là varchar(MAX)
            $table->string('Name');
            $table->text('Describle');
            $table->text('Origin');

            // AppraiserStatus là enum ['appraised', 'unappraised'], isNew là enum ['new', 'old']
            // isInAuction là enum ['inauctioned', 'nonauctioned'], isSold là enum ['sold', 'unsold']
            $table->enum('AppraiserStatus', ['appraised', 'unappraised']);
            $table->enum('isNew', ['new', 'old']);
            $table->enum('isInAuction', ['inauctioned', 'nonauctioned']);
            $table->enum('isSold', ['sold', 'unsold']);

            // UserId, AppraiserId, WareHouseId, CategoryId làm khóa ngoại, varchar(10)
            $table->string('UserId', 10);
            $table->string('AppraiserId', 10);
            $table->string('WareHouseId', 10);
            $table->string('CategoryId', 10);

            // Định nghĩa các ràng buộc khóa ngoại
            $table->foreign('UserId')->references('UserId')->on('users');
            $table->foreign('AppraiserId')->references('AppraiserId')->on('appraisers');
            $table->foreign('WareHouseId')->references('WareHouseId')->on('ware_houses');
            $table->foreign('CategoryId')->references('CategoryId')->on('categories');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('assets');
    }
}
