<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateAppraisersTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('appraisers', function (Blueprint $table) {
            // AppraiserId làm khóa chính, varchar(10)
            $table->string('AppraiserId', 10)->primary();

            // Name, Experience, Email, Phone, Address là varchar(MAX)
            $table->string('Name');
            $table->string('Experience');
            $table->string('Email');
            $table->string('Phone');
            $table->string('Address');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('appraisers');
    }
}
