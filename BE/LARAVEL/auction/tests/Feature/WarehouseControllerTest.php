<?php

namespace Tests\Feature;

use App\Models\User;
use App\Models\Warehouse;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Tests\TestCase;

class WarehouseControllerTest extends TestCase
{
    use RefreshDatabase;

    protected $user;

    public function setUp(): void
    {
        parent::setUp();
        $this->user = User::factory()->create();
        $this->actingAs($this->user);
    }

    public function testIndex()
    {
        Warehouse::factory()->count(3)->create();
        $response = $this->getJson('/warehouses');
        $response->assertStatus(200)
            ->assertJsonCount(3);
    }

    public function testStore()
    {
        $data = [
            'WareHouseId' => 'WH00000001',
            'Position' => 'A1',
            'Quantity' => 100.5,
            'importDate' => '2024-07-14 10:00:00',
            'exportDate' => '2024-07-15 15:30:00',
        ];
        $response = $this->postJson('/warehouses', $data);
        $response->assertStatus(201)
            ->assertJsonStructure(['WareHouseId', 'Position', 'Quantity', 'importDate', 'exportDate'])
            ->assertJson($data);
    }

    public function testShow()
    {
        $warehouse = Warehouse::factory()->create();
        $response = $this->getJson("/warehouses/{$warehouse->WareHouseId}");
        $response->assertStatus(200)
            ->assertJson($warehouse->toArray());
    }

    public function testUpdate()
    {
        $warehouse = Warehouse::factory()->create();
        $data = [
            'WareHouseId' => 'WH00000002',
            'Position' => 'Updated Position',
            'Quantity' => 150.5,
            'importDate' => '2024-07-15 09:00:00',
            'exportDate' => '2024-07-16 14:00:00',
        ];
        $response = $this->putJson("/warehouses/{$warehouse->WareHouseId}", $data);
        $response->assertStatus(200)
            ->assertJson($data);
    }

    public function testDestroy()
    {
        $warehouse = Warehouse::factory()->create();
        $response = $this->deleteJson("/warehouses/{$warehouse->WareHouseId}");
        $response->assertStatus(200)
            ->assertJson(['message' => 'Warehouse deleted successfully']);
        $this->assertDatabaseMissing('warehouses', ['id' => $warehouse->WareHouseId]);
    }

    public function testStoreValidation()
    {
        $response = $this->postJson('/warehouses', []);
        $response->assertStatus(422)
            ->assertJsonValidationErrors(['WareHouseId', 'Position', 'Quantity', 'importDate', 'exportDate']);
    }

    public function testShowNotFound()
    {
        $response = $this->getJson("/warehouses/WH00000001");
        $response->assertStatus(404);
    }
}
